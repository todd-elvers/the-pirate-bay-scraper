# the-pirate-bay-scraper (TPBS)

This application scrapes ThePirateBay and renders the output in a local, sortable HTML page that looks almost exactly like
ThePirateBay.

<br/>

## How it works

When executed, this application opens a small GUI that asks the user for 3 things:

1. The [MediaType](https://github.com/todd-elvers/the-pirate-bay-scraper/blob/master/src/main/groovy/te/tpb/scraper/domain/MediaType.groovy) they're looking for (e.g. PC_GAMES)
2. The seeder threshold they're looking for (i.e. number of seeders a result has to have to be picked up by the scraper)
3. The number of pages to scrape.  (The 'browse torrents' feature of ThePirateBay shows you the most recent additions to
a particular MediaType in paginated form.  This value determines how many of those paginated links to travel back through
before scraping is considered complete.)

When the user is ready, they press __Scrape__ and the following happens:

* The application uses multi-threading to quickly scrape all relevant URLs
* The `<tr>` elements on the results pages that surpass the user's provided seeder threshold are extracted
* All gathered `<tr>` elements are merged into one large list of `<tr>` elements
* The large list of `<tr>` elements are injected into an HTML template whose styles closely resembles ThePirateBay
* The resulting HTML is then formatted so all the links work locally & then written to a file in the system's temp directory
* That file is then opened using the system's default browser
* The application terminates

<br/>

## How to run this

You can simply use the Windows executable [here](https://github.com/todd-elvers/the-pirate-bay-scraper/releases/download/3.0.0/tpbs.exe). Note that the executable is rather large due to the fact that it has the entire Groovy library it needs zipped inside of it so that one only needs Java to run the application.  If the large executable makes you wary, you can just un-zip the exectuable and look at the class files themselves.

### Or
You could download the JAR and execute it with `java -jar <JAR-name>`.

### Or
You could clone the project and execute `./gradlew bootRun` from the root.

<br/>

## Worthy mentions

* The parsing of the HTML is done with sanity thanks to [Jsoup](http://jsoup.org/) and its support for CSS selectors.
* The HTML file that contains the scraping results is sortable due to a nice client-side tablesorting library called [jquery-tablesorter](https://github.com/christianbach/tablesorter).
* Thanks to ThePirateBay for being awesome.

<br/>

## License
This application has been released under the MIT License (MIT).
