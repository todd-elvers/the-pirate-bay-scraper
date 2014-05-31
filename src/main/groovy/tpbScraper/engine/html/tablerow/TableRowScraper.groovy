package tpbScraper.engine.html.tablerow
import java.util.regex.Matcher

class TableRowScraper {

    StringBuilder scrapeTableRowElementsFromHTML(StringBuilder html, int seederThreshold) {
        StringBuilder tableRows = new StringBuilder()
        Matcher tableRowMatcher, seedCountMatcher

        tableRowMatcher = (html.toString() =~ /(<tr>)(.*?)(<\/tr>)/)

        if (tableRowMatcher){
            for(int i = 0; i < tableRowMatcher.count - 1; i++){
                seedCountMatcher = (tableRowMatcher[i][2] =~ /(<td align="right">)(.*?)(<\/td>)/)
                int seedCount = Integer.parseInt(seedCountMatcher[0][2].toString().trim());

                if (seedCount >= seederThreshold){
                    tableRows.append(tableRowMatcher[i][0])
                }
            }
        }

        return tableRows
    }
}
