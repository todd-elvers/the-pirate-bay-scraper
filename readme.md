#the-pirate-bay-scraper (TBPS)

This application scrapes ThePirateBay.se and renders the output in a local HTML page that looks almost exactly like
ThePirateBay.se.

<br/>

##How it works

When executed, this application opens a small GUI that asks the user for 3 things:
1. The MediaType they're looking for (e.g. PC_GAMES)
2. The seeder threshold they're looking for (i.e. number of seeders a result has to have to be picked up by the scraper)
3. The number of pages to scrape.  The 'browse torrents' feature of ThePirateBay shows you the most recent additions to
a particular MediaType in paginated form.  This value determines how many of those paginated links to follow before stoping
scraping.

After the 'Scrape' button is pressed, the application uses multi-threading to quickly scrape all relevant URLs.

The results are then formatted so all links work & injected into a template that very closely resembles ThePirateBay's styles.

Finally, the formatted HTML is written to a file in the system's temporary directory and that file is passed to Chrome.

<br/>

##License
This application has been released under the MIT License (MIT).
