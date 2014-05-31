(function($, window, undefined){
	$(document).ready(function() {
		$tableContainingSearchResults = $('#searchResult');
		
		$tableContainingSearchResults.tablesorter({ 
			sortList: [[2,1]],			// Default sort: by seeders descending
			widgets: ['staticRow'] 		// Allows us to make last TR element unsortable 
		});
	});
})(jQuery, window);