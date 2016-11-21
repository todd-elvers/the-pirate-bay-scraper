package tpbScraper.engine.html.document;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.function.Predicate;

// TODO: Replace the Groovy version w/ this class
// TODO: Merge downloader & extractor into one?  Maybe make a parent class with both or something?
@Component
class HtmlDocumentExtractorJava {

    private static final String ALL_NON_DIGITS_REGEX = "[^0-9]";

    StringBuilder extractRowsAboveThreshold(Document document, int seederCountThreshold){
        StringBuilder tableRowsHtml = new StringBuilder();

        // Find all tr elements (excluding the header row)
        document.select("#searchResult tr:not(.header)")
                .stream()
                .filter(outRowsWithSeederCountsBelow(seederCountThreshold))
                .forEach(tr -> tableRowsHtml.append(tr.outerHtml()));

        return  tableRowsHtml;
    }

    private Predicate<Element> outRowsWithSeederCountsBelow(int threshold) {
        return (tr) -> {
            Element td = tr.select("td[align=\"right\"]").first();
            return td != null && Integer.parseInt(td.text().replaceAll(ALL_NON_DIGITS_REGEX, "")) >= threshold;
        };
    }

}
