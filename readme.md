#the-pirate-bay-scraper (TBPS)

This application scrapes ThePirateBay.se and renders the output in a local HTML page that looks almost exactly like
ThePirateBay.se.

<br/>

##How it works

When executed, this application opens a small GUI that asks the user for 3 things:

1. The [MediaType](https://github.com/todd-elvers/the-pirate-bay-scraper/blob/master/src/main/groovy/tpbScraper/domain/MediaType.groovy) they're looking for (e.g. PC_GAMES)
2. The seeder threshold they're looking for (i.e. number of seeders a result has to have to be picked up by the scraper)
3. The number of pages to scrape.  (The 'browse torrents' feature of ThePirateBay shows you the most recent additions to
a particular MediaType in paginated form.  This value determines how many of those paginated links to travel back through
before scraping is considered complete.)

When the user is ready, they press __Scrape__ and the following happens:

* The application uses multi-threading to quickly scrape all relevant URLs
* The table rows for each page are merged into one large group of tr & td elements
* The table rows are then formatted so all their links work
* The table rows is then injected into a template that very closely resembles ThePirateBay's results page
* The HTML is written to a file in the system's temporary directory
* The HTML file is then passed to Chrome & the application terminates

<br/>

##Installation

Download the latest version [here](https://github.com/todd-elvers/the-pirate-bay-scraper/releases/download/2.0.0/tpbs.exe).

##License
This application has been released under the MIT License (MIT).
