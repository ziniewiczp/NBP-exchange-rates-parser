package pl.parser.nbp;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Parser {
    private static Document loadDocument(String url) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        return factory.newDocumentBuilder().parse(new URL(url).openStream());
    }

    public ParsedData parseData(String currency, String stringStartDate, String stringEndDate) throws Exception {
        ParsedData data = new ParsedData();
        int i = 0;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(stringStartDate, formatter);
        LocalDate endDate = LocalDate.parse(stringEndDate, formatter);

        for (LocalDate date = startDate; date.isBefore(endDate.plusDays(1)); date = date.plusDays(1)) {
            String formattedDate = date.toString().substring(2, 4) + date.toString().substring(5, 7)
                    + date.toString().substring(8, 10);
            String dirSuffix = "";

            // dir.txt contains data from current year; data from previous years
            // is stored in files dir2002.txt, dir2003.txt, etc.
            if (date.getYear() != Year.now().getValue()) {
                dirSuffix = "20" + formattedDate.substring(0, 2);
            }

            URL url = new URL("http://www.nbp.pl/kursy/xml/dir" + dirSuffix + ".txt");
            Scanner scanner = new Scanner(url.openStream());

            // looping through dir.txt
            while (scanner.hasNextLine()) {
                String string = scanner.nextLine();

                if (string.matches("^c...." + formattedDate)) {
                    Document doc = loadDocument("http://www.nbp.pl/kursy/xml/" + string + ".xml");

                    NodeList nList = doc.getElementsByTagName("pozycja");
                    for (int temp = 0; temp < nList.getLength(); temp++) {
                        Node nNode = nList.item(temp);

                        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element eElement = (Element) nNode;

                            if (eElement.getElementsByTagName("kod_waluty").item(0).getTextContent().equals(currency)) {
                                data.averageSum = data.averageSum
                                        .add(new BigDecimal(eElement.getElementsByTagName("kurs_kupna").item(0)
                                                .getTextContent().replaceAll(",", ".")));
                                data.deviationSum = data.deviationSum
                                        .add(new BigDecimal(eElement.getElementsByTagName("kurs_sprzedazy").item(0)
                                                .getTextContent().replaceAll(",", ".")));

                                data.deviationList.add(new BigDecimal(eElement.getElementsByTagName("kurs_sprzedazy")
                                        .item(0).getTextContent().replaceAll(",", ".")));
                                break;
                            }
                        }
                    }
                }
            }
            scanner.close();
            i++;
        }

        data.counter = new BigDecimal(i);

        return data;
    }
}
