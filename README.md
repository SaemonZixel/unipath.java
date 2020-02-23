# unipath.java

Require: Java 8 and upper.

Simple examples
---------------

	UniPath.eval("/String/format('<h1>%s</h1>', 'ok!')");
	// "<h1>ok!</h1>"
	
	new UniPath("/String").get("format(<h1>%s</h1>, 'ok!')");
	// "<h1>ok!</h1>"

Debugging
---------

	// print a debugging info when evaluating a unipath expression (use System.out)
	UniPath.debug = true; 
	
	// print a debugging info when parse a unipath expression (use System.out)
	UniPath.debug_parse = true;
	
	// command for start the tests in a terminal
	javac UniPath_tests.java && java -ea UniPath_tests 