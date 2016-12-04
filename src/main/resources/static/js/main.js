$( function() {
  var availableTags = [
    "ActionScript",
    "AppleScript",
    "Asp",
    "BASIC",
    "C",
    "C++",
    "Clojure",
    "COBOL",
    "ColdFusion",
    "Erlang",
    "Fortran",
    "Groovy",
    "Haskell",
    "Java",
    "JavaScript",
    "Lisp",
    "Perl",
    "PHP",
    "Python",
    "Ruby",
    "Scala",
    "Scheme"
  ];
  // $( "#user_input" ).autocomplete({
  //   // source: availableTags
  //   source:"http://localhost:8080/querykeys"
  // });
  $("#user_input").autocomplete({
				delay: 500,
				minLength: 3,
				source: function(request, response) {
					$.getJSON("http://localhost:8080/querykeys", {
						// do not copy the api key; get your own at developer.rottentomatoes.com
						term: request.term,
					}, function(data) {
            console.log(data);
						response(data);
					});
				},
				focus: function(event, ui) {
					// prevent autocomplete from updating the textbox
					// event.preventDefault();
          // $(this).val(ui.item.label);
				},
				select: function(event, ui) {
					// prevent autocomplete from updating the textbox
					event.preventDefault();
          $(this).val(ui.item.label);
					// navigate to the selected item's url
					// window.open(ui.item.url);
				}
			});
} );
