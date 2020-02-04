import java.util.*;
import java.io.*;

class UniPath_tests {
	static public void main(String[] args) {
		UniPath.__prt_cnt = 200;
	
		test_parseUniPath();
// 		test_uniPath();
	}
	
	static public void test_parseUniPath() {
		UniPath uni = new UniPath();
		String unipath;
		ArrayList<UniPath.Node> tree;
		
		unipath = "/java.util.HashMap<String, HashMap<Integer, Object[]>>/new()/class";
		System.out.format("%n\033[1m~~~~ %s ~~~~\033[0m%n", unipath);
// 		UniPath.debug_parse = true;
		tree = UniPath.__parseUniPath(unipath.toCharArray(), null, null);
// System.out.println(UniPath.call("toJson", tree));
		assert equ(tree, ms(/*[{
  "name": "?start_data?", 
  "filter": null, 
  "data": null, 
  "metadata": {"0":"null"}, 
  "unipath": ""
},{
  "name": "java.util.HashMap<String, HashMap<Integer, Object[]>>", 
  "filter": null, 
  "data": null, 
  "metadata": {}, 
  "unipath": "\/java.util.HashMap<String, HashMap<Integer, Object[]>>"
},{
  "name": "new()", 
  "filter": null, 
  "data": null, 
  "metadata": {}, 
  "unipath": "\/java.util.HashMap<String, HashMap<Integer, Object[]>>\/new()"
},{
  "name": "class", 
  "filter": null, 
  "data": null, 
  "metadata": {}, 
  "unipath": "\/java.util.HashMap<String, HashMap<Integer, Object[]>>\/new()\/class"
}]*/));
		
		unipath = "/objs[1+2*3 > @obj_id and @obj_id = 7]";
		System.out.format("%n\033[1m~~~~ %s ~~~~\033[0m%n", unipath);
// 		UniPath.debug_parse = true;
		tree = UniPath.__parseUniPath(unipath.toCharArray(), null, null);
// System.out.println(UniPath.call("toJson", tree));
		assert equ(tree, ms(/*[{
  "name": "?start_data?", 
  "filter": null, 
  "data": null, 
  "metadata": {"0":"null"}, 
  "unipath": ""
},{
  "name": "objs", 
  "filter": [{
      "left": null, 
      "left_type": null, 
      "op": null, 
      "right": null, 
      "right_type": null, 
      "next": 2, 
      "prev": null, 
      "braket_level": 0
    },{
      "left": "1", 
      "left_type": "number", 
      "op": "+", 
      "right": 2, 
      "right_type": "expr", 
      "next": 3, 
      "prev": null, 
      "braket_level": 0
    },{
      "left": "2", 
      "left_type": "number", 
      "op": "*", 
      "right": "3", 
      "right_type": "number", 
      "next": 1, 
      "prev": null, 
      "braket_level": 0
    },{
      "left": 1, 
      "left_type": "expr", 
      "op": ">", 
      "right": "@obj_id", 
      "right_type": "name", 
      "next": 5, 
      "prev": null, 
      "braket_level": 0
    },{
      "left": 3, 
      "left_type": "expr", 
      "op": "and", 
      "right": 5, 
      "right_type": "expr", 
      "next": null, 
      "prev": null, 
      "braket_level": 0
    },{
      "left": "@obj_id", 
      "left_type": "name", 
      "op": "=", 
      "right": "7", 
      "right_type": "number", 
      "next": 4, 
      "prev": null, 
      "braket_level": 0
    }], 
  "data": null, 
  "metadata": {}, 
  "unipath": "\/objs[1+2*3 > @obj_id and @obj_id = 7]"
}]*/));
		
		unipath = "/objs[11+22*(33+44)]";
		System.out.format("%n\033[1m~~~~ %s ~~~~\033[0m%n", unipath);
// 		UniPath.debug_parse = true;
		tree = UniPath.__parseUniPath(unipath.toCharArray(), null, null);
// System.out.println(UniPath.call("toJson", tree));
		assert equ(tree, ms(/*[{
  "name": "?start_data?", 
  "filter": null, 
  "data": null, 
  "metadata": {"0":"null"}, 
  "unipath": ""
},{
  "name": "objs",
  "filter": [{
      "left": null, 
      "left_type": null, 
      "op": null, 
      "right": null, 
      "right_type": null, 
      "next": 3, 
      "prev": null, 
      "braket_level": 0
    },{
      "left": "11", 
      "left_type": "number", 
      "op": "+", 
      "right": 2, 
      "right_type": "expr", 
      "next": null, 
      "prev": null, 
      "braket_level": 0
    },{
      "left": "22", 
      "left_type": "number", 
      "op": "*", 
      "right": 3, 
      "right_type": "expr", 
      "next": 1, 
      "prev": null, 
      "braket_level": 0
    },{
      "left": "33", 
      "left_type": "number", 
      "op": "+", 
      "right": "44", 
      "right_type": "number", 
      "next": 2, 
      "prev": null, 
      "braket_level": 1
    }], 
  "data": null, 
  "metadata": {}, 
  "unipath": "\/objs[11+22*(33+44)]"
}]*/));

		unipath = "/objs[11+22*(33+44)*55]";
		System.out.format("%n\033[1m~~~~ %s ~~~~\033[0m%n", unipath);
// 		UniPath.debug_parse = true;
		tree = UniPath.__parseUniPath(unipath.toCharArray(), null, null);
// System.out.println(UniPath.call("toJson", tree));
		assert equ(tree, ms(/*[{
	"name":"?start_data?",
	"filter":null,
	"data":null,
	"metadata":{"0":"null"},
	"unipath":""
},{
	"name":"objs",
	"filter":[{
		"left":null,
		"left_type":null,
		"op":null,
		"right":null,
		"right_type":null,
		"next":3,
		"prev":null,
		"braket_level":0
	},{
		"left":"11",
		"left_type":"number",
		"op":"+",
		"right":2,
		"right_type":"expr",
		"next":null,
		"prev":null,
		"braket_level":0
	},{
		"left":"22",
		"left_type":"number",
		"op":"*",
		"right":3,
		"right_type":"expr",
		"next":1,
		"prev":null,
		"braket_level":0
	},{
		"left":"33",
		"left_type":"number",
		"op":"+",
		"right":"44",
		"right_type":"number",
		"next":4,
		"prev":null,
		"braket_level":1
	},{
		"left":3,
		"left_type":"expr",
		"op":"*",
		"right":"55",
		"right_type":"number",
		"next":2,
		"prev":null,
		"braket_level":0
	}],
	"data":null,
	"metadata":{},
	"unipath":"\/objs[11+22*(33+44)*55]"
}]*/));
// UniPath.debug_parse = false;

		unipath = "/objs[11*22+33*44*55]";
		System.out.format("%n\033[1m~~~~ %s ~~~~\033[0m%n", unipath);
// 		UniPath.debug_parse = true;
		tree = UniPath.__parseUniPath(unipath.toCharArray(), null, null);
// System.out.println(UniPath.call("toJson", tree));
		assert equ(tree, ms(/*[{
  "name": "?start_data?", 
  "filter": null, 
  "data": null, 
  "metadata": {"0":"null"}, 
  "unipath": ""
},{
  "name": "objs", 
  "filter": [{
      "left": null, 
      "left_type": null, 
      "op": null, 
      "right": null, 
      "right_type": null, 
      "next": 1, 
      "prev": null, 
      "braket_level": 0
    },{
      "left": "11", 
      "left_type": "number", 
      "op": "*", 
      "right": "22", 
      "right_type": "number", 
      "next": 3, 
      "prev": null, 
      "braket_level": 0
    },{
      "left": 1, 
      "left_type": "expr", 
      "op": "+", 
      "right": 4, 
      "right_type": "expr", 
      "next": null, 
      "prev": null, 
      "braket_level": 0
    },{
      "left": "33", 
      "left_type": "number", 
      "op": "*", 
      "right": "44", 
      "right_type": "number", 
      "next": 4, 
      "prev": null, 
      "braket_level": 0
    },{
      "left": 3, 
      "left_type": "expr", 
      "op": "*", 
      "right": "55", 
      "right_type": "number", 
      "next": 2, 
      "prev": null, 
      "braket_level": 0
    }], 
  "data": null, 
  "metadata": {}, 
  "unipath": "\/objs[11*22+33*44*55]"
}]*/));

		unipath = "/default_Orchard_Framework_ContentItemRecord[ContentType_id=11,14 and Published=1 and (like(Data, N'%abc%') or ContentItemRecord_id = 'abc' or like(Title, N'%abc%'))]";
		System.out.format("%n\033[1m~~~~ %s ~~~~\033[0m%n", unipath);
// 		UniPath.debug_parse = true;
		tree = UniPath.__parseUniPath(unipath.toCharArray(), null, null);
// System.out.println(UniPath.call("toJson", tree));
		assert equ(tree, ms(/*[{
  "name": "?start_data?", 
  "filter": null, 
  "data": null, 
  "metadata": {"0":"null"}, 
  "unipath": ""
},{
  "name": "default_Orchard_Framework_ContentItemRecord", 
  "filter": [{
      "left": null, 
      "left_type": null, 
      "op": null, 
      "right": null, 
      "right_type": null, 
      "next": 1, 
      "prev": null, 
      "braket_level": 0
    },{
      "left": "ContentType_id", 
      "left_type": "name", 
      "op": "=", 
      "right": ["11","14"], 
      "right_type": "list-of-number", 
      "next": 3, 
      "prev": null, 
      "braket_level": 0
    },{
      "left": 1, 
      "left_type": "expr", 
      "op": "and", 
      "right": 3, 
      "right_type": "expr", 
      "next": 6, 
      "prev": null, 
      "braket_level": 0
    },{
      "left": "Published", 
      "left_type": "name", 
      "op": "=", 
      "right": "1", 
      "right_type": "number", 
      "next": 2, 
      "prev": null, 
      "braket_level": 0
    },{
      "left": 2, 
      "left_type": "expr", 
      "op": "and", 
      "right": 7, 
      "right_type": "expr", 
      "next": null, 
      "prev": null, 
      "braket_level": 0
    },{
      "left": "like(Data, N'%abc%')", 
      "left_type": "function", 
      "op": "or", 
      "right": 6, 
      "right_type": "expr", 
      "next": 7, 
      "prev": null, 
      "braket_level": 1
    },{
      "left": "ContentItemRecord_id", 
      "left_type": "name", 
      "op": "=", 
      "right": "abc", 
      "right_type": "string", 
      "next": 5, 
      "prev": null, 
      "braket_level": 1
    },{
      "left": 5, 
      "left_type": "expr", 
      "op": "or", 
      "right": "like(Title, N'%abc%')", 
      "right_type": "function", 
      "next": 4, 
      "prev": null, 
      "braket_level": 1
    }], 
  "data": null, 
  "metadata": {}, 
  "unipath": "\/default_Orchard_Framework_ContentItemRecord[ContentType_id=11,14 and Published=1 and (like(Data, N'%abc%') or ContentItemRecord_id = 'abc' or like(Title, N'%abc%'))]"
}]*/));
		
		unipath = "/objs[@name = 'dummy']";
		System.out.format("%n\033[1m~~~~ %s ~~~~\033[0m%n", unipath);
// 		UniPath.debug_parse = true;
		tree = UniPath.__parseUniPath(unipath.toCharArray(), null, null);
// System.out.println(UniPath.call("toJson", tree));
		assert equ(tree, ms(/*[{
  "name": "?start_data?", 
  "filter": null, 
  "data": null, 
  "metadata": {"0":"null"}, 
  "unipath": ""
},{
  "name": "objs", 
  "filter": [{
      "left": null, 
      "left_type": null, 
      "op": null, 
      "right": null, 
      "right_type": null, 
      "next": 1, 
      "prev": null, 
      "braket_level": 0
    },{
      "left": "@name", 
      "left_type": "name", 
      "op": "=", 
      "right": "dummy", 
      "right_type": "string", 
      "next": null, 
      "prev": null, 
      "braket_level": 0
    }], 
  "data": null, 
  "metadata": {}, 
  "unipath": "\/objs[@name = 'dummy']"
}]*/));
		
		unipath = "/objs[@id=basket_order_id()]/order_items[rel_val=1]/asObj()/item_link/asPage()/asObj()/.[@data_type='optioned']";
		System.out.format("%n\033[1m~~~~ %s ~~~~\033[0m%n", unipath);
// 		UniPath.debug_parse = true;
		tree = UniPath.__parseUniPath(unipath.toCharArray(), null, null);
// System.out.println(UniPath.call("toJson", tree));
		assert equ(tree, ms(/*[{}]*/));
		
		unipath = "/objs[@name = 'Сочи, Чебрикова 7, кв. 32' and @owner_id = current_user_id()]/@id";
		System.out.format("%n\033[1m~~~~ %s ~~~~\033[0m%n", unipath);
// 		UniPath.debug_parse = true;
		tree = UniPath.__parseUniPath(unipath.toCharArray(), null, null);
// System.out.println(UniPath.call("toJson", tree));
		assert equ(tree, ms(/*[{
  "name": "?start_data?", 
  "filter": null, 
  "data": null, 
  "metadata": {"0":"null"}, 
  "unipath": ""
},{
  "name": "objs", 
  "filter": [{
      "left": null, 
      "left_type": null, 
      "op": null, 
      "right": null, 
      "right_type": null, 
      "next": 1, 
      "prev": null, 
      "braket_level": 0
    },{
      "left": "@name", 
      "left_type": "name", 
      "op": "=", 
      "right": "Сочи, Чебрикова 7, кв. 32", 
      "right_type": "string", 
      "next": 3, 
      "prev": null, 
      "braket_level": 0
    },{
      "left": 1, 
      "left_type": "expr", 
      "op": "and", 
      "right": 3, 
      "right_type": "expr", 
      "next": null, 
      "prev": null, 
      "braket_level": 0
    },{
      "left": "@owner_id", 
      "left_type": "name", 
      "op": "=", 
      "right": "current_user_id()", 
      "right_type": "function", 
      "next": 2, 
      "prev": null, 
      "braket_level": 0
    }], 
  "data": null, 
  "metadata": {}, 
  "unipath": "\/objs[@name = 'Сочи, Чебрикова 7, кв. 32' and @owner_id = current_user_id()]"
},{
  "name": "@id", 
  "filter": null, 
  "data": null, 
  "metadata": {}, 
  "unipath": "\/objs[@name = 'Сочи, Чебрикова 7, кв. 32' and @owner_id = current_user_id()]\/@id"
}]*/));
		
		unipath = "/objs[@owner_id=10012 and @type_id=24 and status_id<>15 and domain_id=1]";
		System.out.format("%n\033[1m~~~~ %s ~~~~\033[0m%n", unipath);
// 		UniPath.debug_parse = true;
		tree = UniPath.__parseUniPath(unipath.toCharArray(), null, null);
// System.out.println(UniPath.call("toJson", tree));
		assert equ(tree, ms(/*[{
  "name": "?start_data?", 
  "filter": null, 
  "data": null, 
  "metadata": {"0":"null"}, 
  "unipath": ""
},{
  "name": "objs", 
  "filter": [{
      "left": null, 
      "left_type": null, 
      "op": null, 
      "right": null, 
      "right_type": null, 
      "next": 1, 
      "prev": null, 
      "braket_level": 0
    },{
      "left": "@owner_id", 
      "left_type": "name", 
      "op": "=", 
      "right": "10012", 
      "right_type": "number", 
      "next": 3, 
      "prev": null, 
      "braket_level": 0
    },{
      "left": 1, 
      "left_type": "expr", 
      "op": "and", 
      "right": 3, 
      "right_type": "expr", 
      "next": 5, 
      "prev": null, 
      "braket_level": 0
    },{
      "left": "@type_id", 
      "left_type": "name", 
      "op": "=", 
      "right": "24", 
      "right_type": "number", 
      "next": 2, 
      "prev": null, 
      "braket_level": 0
    },{
      "left": 2, 
      "left_type": "expr", 
      "op": "and", 
      "right": 5, 
      "right_type": "expr", 
      "next": 7, 
      "prev": null, 
      "braket_level": 0
    },{
      "left": "status_id", 
      "left_type": "name", 
      "op": "<>", 
      "right": "15", 
      "right_type": "number", 
      "next": 4, 
      "prev": null, 
      "braket_level": 0
    },{
      "left": 4, 
      "left_type": "expr", 
      "op": "and", 
      "right": 7, 
      "right_type": "expr", 
      "next": null, 
      "prev": null, 
      "braket_level": 0
    },{
      "left": "domain_id", 
      "left_type": "name", 
      "op": "=", 
      "right": "1", 
      "right_type": "number", 
      "next": 6, 
      "prev": null, 
      "braket_level": 0
    }], 
  "data": null, 
  "metadata": {}, 
  "unipath": "\/objs[@owner_id=10012 and @type_id=24 and status_id<>15 and domain_id=1]"
}]*/));
		
// 		unipath = "/db1/products[prd_deleted=0 and prd_hidden=0][prd_name/regexp_match('abc') or prd_articul/regexp_match('abc')]/sort(prd_sort_order)";

		unipath = "/db1/products[prd_name/regexp_match('789') \n"+
"		or prd_articul/regexp_match('789') \n"+
"		or prd_data_json_encoded/asJSON()/descr/ifEmpty('')/regexp_match('789')\n"+
"		or prd_data_json_encoded/asJSON()/content/ifEmpty('')/regexp_match('789') ]/sort(prd_sort_order)";
		System.out.format("%n\033[1m~~~~ %s ~~~~\033[0m%n", unipath);
// 		UniPath.debug_parse = true;
		tree = UniPath.__parseUniPath(unipath.toCharArray(), null, null);
// System.out.println(UniPath.call("toJson", tree));
		assert equ(tree, ms(/*[{
  "name": "?start_data?", 
  "filter": null, 
  "data": null, 
  "metadata": {"0":"null"}, 
  "unipath": ""
},{
  "name": "db1", 
  "filter": null, 
  "data": null, 
  "metadata": {}, 
  "unipath": "\/db1"
},{
  "name": "products",
  "filter": [{
      "left": null, 
      "left_type": null, 
      "op": null, 
      "right": null, 
      "right_type": null, 
      "next": 1, 
      "prev": null, 
      "braket_level": 0
    },{
      "left": "prd_name\/regexp_match('789')", 
      "left_type": "unipath", 
      "op": "or", 
      "right": "prd_articul\/regexp_match('789')", 
      "right_type": "unipath", 
      "next": 2, 
      "prev": null, 
      "braket_level": 0
    },{
      "left": 1, 
      "left_type": "expr", 
      "op": "or", 
      "right": "prd_data_json_encoded\/asJSON()\/descr\/ifEmpty('')\/regexp_match('789')", 
      "right_type": "unipath", 
      "next": 3, 
      "prev": null, 
      "braket_level": 0
    },{
      "left": 2, 
      "left_type": "expr", 
      "op": "or", 
      "right": "prd_data_json_encoded\/asJSON()\/content\/ifEmpty('')\/regexp_match('789')", 
      "right_type": "unipath", 
      "next": null, 
      "prev": null, 
      "braket_level": 0
    }], 
  "data": null, 
  "metadata": {}, 
  "unipath": "\/db1\/products[prd_name\/regexp_match('789') \n\t\tor prd_articul\/regexp_match('789') \n\t\tor prd_data_json_encoded\/asJSON()\/descr\/ifEmpty('')\/regexp_match('789')\n\t\tor prd_data_json_encoded\/asJSON()\/content\/ifEmpty('')\/regexp_match('789') ]"
},{
  "name": "sort(prd_sort_order)", 
  "filter": null, 
  "data": null, 
  "metadata": {}, 
  "unipath": "\/db1\/products[prd_name\/regexp_match('789') \n\t\tor prd_articul\/regexp_match('789') \n\t\tor prd_data_json_encoded\/asJSON()\/descr\/ifEmpty('')\/regexp_match('789')\n\t\tor prd_data_json_encoded\/asJSON()\/content\/ifEmpty('')\/regexp_match('789') ]\/sort(prd_sort_order)"
}]*/));

		unipath = "/db1/servicesimages[item_id=1 , 2\n"+
"	, 3 AND service_id=5]\n"+
"		+images[image_id=id]\n"+
"		/columns(`servicesimages.item_id AS pn_id, image_id, name, type, weight`)\n"+
"		/limit(2)/all()";
		System.out.format("%n\033[1m~~~~ %s ~~~~\033[0m%n", unipath);
// 		UniPath.debug_parse = true;
		tree = UniPath.__parseUniPath(unipath.toCharArray(), null, null);
// System.out.println(UniPath.call("toJson", tree));
		assert equ(tree, ms(/*[{
  "name": "?start_data?", 
  "filter": null, 
  "data": null, 
  "metadata": {"0":"null"}, 
  "unipath": ""
},{
  "name": "db1", 
  "filter": null, 
  "data": null, 
  "metadata": {}, 
  "unipath": "\/db1"
},{
  "name": "servicesimages", 
  "filter": [{
      "left": null, 
      "left_type": null, 
      "op": null, 
      "right": null, 
      "right_type": null, 
      "next": 1, 
      "prev": null, 
      "braket_level": 0
    },{
      "left": "item_id", 
      "left_type": "name", 
      "op": "=", 
      "right": ["1","2","3"], 
      "right_type": "list-of-number", 
      "next": 3, 
      "prev": null, 
      "braket_level": 0
    },{
      "left": 1, 
      "left_type": "expr", 
      "op": "and", 
      "right": 3, 
      "right_type": "expr", 
      "next": null, 
      "prev": null, 
      "braket_level": 0
    },{
      "left": "service_id", 
      "left_type": "name", 
      "op": "=", 
      "right": "5", 
      "right_type": "number", 
      "next": 2, 
      "prev": null, 
      "braket_level": 0
    }],
  "data": null, 
  "metadata": {}, 
  "unipath": "\/db1\/servicesimages[item_id=1 , 2\n\t, 3 AND service_id=5]\n\t\t"
},{
  "name": "+images", 
  "filter": [{
      "left": null, 
      "left_type": null, 
      "op": null, 
      "right": null, 
      "right_type": null, 
      "next": 1, 
      "prev": null, 
      "braket_level": 0
    },{
      "left": "image_id", 
      "left_type": "name", 
      "op": "=", 
      "right": "id", 
      "right_type": "name", 
      "next": null, 
      "prev": null, 
      "braket_level": 0
    }], 
  "data": null, 
  "metadata": {}, 
  "unipath": "\/db1\/servicesimages[item_id=1 , 2\n\t, 3 AND service_id=5]\n\t\t+images[image_id=id]\n\t\t"
},{
  "name": "columns(`servicesimages.item_id AS pn_id, image_id, name, type, weight`)",
  "filter": null, 
  "data": null, 
  "metadata": {}, 
  "unipath": "\/db1\/servicesimages[item_id=1 , 2\n\t, 3 AND service_id=5]\n\t\t+images[image_id=id]\n\t\t\/columns(`servicesimages.item_id AS pn_id, image_id, name, type, weight`)\n\t\t"
},{
  "name": "limit(2)", 
  "filter": null, 
  "data": null, 
  "metadata": {}, 
  "unipath": "\/db1\/servicesimages[item_id=1 , 2\n\t, 3 AND service_id=5]\n\t\t+images[image_id=id]\n\t\t\/columns(`servicesimages.item_id AS pn_id, image_id, name, type, weight`)\n\t\t\/limit(2)"
},{
  "name": "all()", 
  "filter": null, 
  "data": null, 
  "metadata": {}, 
  "unipath": "\/db1\/servicesimages[item_id=1 , 2\n\t, 3 AND service_id=5]\n\t\t+images[image_id=id]\n\t\t\/columns(`servicesimages.item_id AS pn_id, image_id, name, type, weight`)\n\t\t\/limit(2)\/all()"
}]*/));
		
		unipath = "/servicesimages[item_id=/barray/items/*/pn_id AND service_id=5]";
		System.out.format("%n\033[1m~~~~ %s ~~~~\033[0m%n", unipath);
// 		UniPath.debug_parse = true;
		tree = UniPath.__parseUniPath(unipath.toCharArray(), null, null);
// System.out.println(UniPath.call("toJson", tree));
		assert equ(tree, ms(/*[{
  "name": "?start_data?", 
  "filter": null, 
  "data": null, 
  "metadata": {"0":"null"}, 
  "unipath": ""
},{
  "name": "servicesimages", 
  "filter": [{
      "left": null, 
      "left_type": null, 
      "op": null, 
      "right": null, 
      "right_type": null, 
      "next": 1, 
      "prev": null, 
      "braket_level": 0
    },{
      "left": "item_id", 
      "left_type": "name", 
      "op": "=", 
      "right": "\/barray\/items\/*\/pn_id", 
      "right_type": "unipath", 
      "next": 3, 
      "prev": null, 
      "braket_level": 0
    },{
      "left": 1, 
      "left_type": "expr", 
      "op": "and", 
      "right": 3, 
      "right_type": "expr", 
      "next": null, 
      "prev": null, 
      "braket_level": 0
    },{
      "left": "service_id", 
      "left_type": "name", 
      "op": "=", 
      "right": "5", 
      "right_type": "number", 
      "next": 2, 
      "prev": null, 
      "braket_level": 0
    }], 
  "data": null, 
  "metadata": {}, 
  "unipath": "\/servicesimages[item_id=\/barray\/items\/*\/pn_id AND service_id=5]"
}]*/));

		unipath = "db1/alias('default_Orchard_Autoroute_AutoroutePartRecord', 'urls')[like(DisplayAlias, '404notfound%') and Published = 1 and Latest = 1]+alias('default_Orchard_Framework_ContentItemVersionRecord','vers')[urls.Id = vers.Id]/columns('vers.ContentItemRecord_id, urls.DisplayAlias, vers.Id as ver_id')";
		System.out.format("%n\033[1m~~~~ %s ~~~~\033[0m%n", unipath);
// 		UniPath.debug_parse = true;
		tree = UniPath.__parseUniPath(unipath.toCharArray(), null, null);
// System.out.println(UniPath.call("toJson", tree));
		assert equ(tree, ms(/*[{
  "name": "?start_data?", 
  "filter": null, 
  "data": null, 
  "metadata": {"0":"null"}, 
  "unipath": ""
},{
  "name": "db1", 
  "filter": null, 
  "data": null, 
  "metadata": {}, 
  "unipath": "db1"
},{
  "name": "alias('default_Orchard_Autoroute_AutoroutePartRecord', 'urls')", 
  "filter": [{
      "left": null, 
      "left_type": null, 
      "op": null, 
      "right": null, 
      "right_type": null, 
      "next": 2, 
      "prev": null, 
      "braket_level": 0
    },{
      "left": "like(DisplayAlias, '404notfound%')", 
      "left_type": "function", 
      "op": "and", 
      "right": 2, 
      "right_type": "expr", 
      "next": 4, 
      "prev": null, 
      "braket_level": 0
    },{
      "left": "Published", 
      "left_type": "name", 
      "op": "=", 
      "right": "1", 
      "right_type": "number", 
      "next": 1, 
      "prev": null, 
      "braket_level": 0
    },{
      "left": 1, 
      "left_type": "expr", 
      "op": "and", 
      "right": 4, 
      "right_type": "expr", 
      "next": null, 
      "prev": null, 
      "braket_level": 0
    },{
      "left": "Latest", 
      "left_type": "name", 
      "op": "=", 
      "right": "1", 
      "right_type": "number", 
      "next": 3, 
      "prev": null, 
      "braket_level": 0
    }], 
  "data": null, 
  "metadata": {}, 
  "unipath": "db1\/alias('default_Orchard_Autoroute_AutoroutePartRecord', 'urls')[like(DisplayAlias, '404notfound%') and Published = 1 and Latest = 1]"
},{
  "name": "+alias('default_Orchard_Framework_ContentItemVersionRecord','vers')", 
  "filter": [{
      "left": null, 
      "left_type": null, 
      "op": null, 
      "right": null, 
      "right_type": null, 
      "next": 1, 
      "prev": null, 
      "braket_level": 0
    },{
      "left": "urls.Id", 
      "left_type": "name", 
      "op": "=", 
      "right": "vers.Id", 
      "right_type": "name", 
      "next": null, 
      "prev": null, 
      "braket_level": 0
    }],
  "data": null, 
  "metadata": {}, 
  "unipath": "db1\/alias('default_Orchard_Autoroute_AutoroutePartRecord', 'urls')[like(DisplayAlias, '404notfound%') and Published = 1 and Latest = 1]+alias('default_Orchard_Framework_ContentItemVersionRecord','vers')[urls.Id = vers.Id]"
},{
  "name": "columns('vers.ContentItemRecord_id, urls.DisplayAlias, vers.Id as ver_id')", 
  "filter": null, 
  "data": null, 
  "metadata": {}, 
  "unipath": "db1\/alias('default_Orchard_Autoroute_AutoroutePartRecord', 'urls')[like(DisplayAlias, '404notfound%') and Published = 1 and Latest = 1]+alias('default_Orchard_Framework_ContentItemVersionRecord','vers')[urls.Id = vers.Id]\/columns('vers.ContentItemRecord_id, urls.DisplayAlias, vers.Id as ver_id')"
}]*/));
		
		unipath = "_POST/cache(file://./unipath_test.json)";
		System.out.format("%n\033[1m~~~~ %s ~~~~\033[0m%n", unipath);
// 		UniPath.debug_parse = true;
		tree = UniPath.__parseUniPath(unipath.toCharArray(), null, null);
// System.out.println(UniPath.call("toJson", tree));
		assert equ(tree, ms(/*[{
  "name": "?start_data?", 
  "filter": null, 
  "data": null, 
  "metadata": {"0":"null"}, 
  "unipath": ""
},{
  "name": "_POST", 
  "filter": null, 
  "data": null, 
  "metadata": {}, 
  "unipath": "_POST"
},{
  "name": "cache(file:\/\/.\/unipath_test.json)", 
  "filter": null, 
  "data": null, 
  "metadata": {}, 
  "unipath": "_POST\/cache(file:\/\/.\/unipath_test.json)"
}]*/));
		
		unipath = "/cms3_object_content[@cms3_hierarchy.is_deleted=0 and @field_id=506] + cms3_hierarchy[@cms3_hierarchy.obj_id = @cms3_object_content.obj_id]";
		System.out.format("%n\033[1m~~~~ %s ~~~~\033[0m%n", unipath);
// 		UniPath.debug_parse = true;
		tree = UniPath.__parseUniPath(unipath.toCharArray(), null, null);
// System.out.println(UniPath.call("toJson", tree));
		assert equ(tree, ms(/*[{
  "name": "?start_data?", 
  "filter": null, 
  "data": null, 
  "metadata": {"0":"null"}, 
  "unipath": ""
},{
  "name": "cms3_object_content", 
  "filter": [{
      "left": null, 
      "left_type": null, 
      "op": null, 
      "right": null, 
      "right_type": null, 
      "next": 1, 
      "prev": null, 
      "braket_level": 0
    },{
      "left": "@cms3_hierarchy.is_deleted", 
      "left_type": "name", 
      "op": "=", 
      "right": "0", 
      "right_type": "number", 
      "next": 3, 
      "prev": null, 
      "braket_level": 0
    },{
      "left": 1, 
      "left_type": "expr", 
      "op": "and", 
      "right": 3, 
      "right_type": "expr", 
      "next": null, 
      "prev": null, 
      "braket_level": 0
    },{
      "left": "@field_id", 
      "left_type": "name", 
      "op": "=", 
      "right": "506", 
      "right_type": "number", 
      "next": 2, 
      "prev": null, 
      "braket_level": 0
    }], 
  "data": null, 
  "metadata": {}, 
  "unipath": "\/cms3_object_content[@cms3_hierarchy.is_deleted=0 and @field_id=506] "
},{
  "name": " cms3_hierarchy", 
  "filter": [{
      "left": null, 
      "left_type": null, 
      "op": null, 
      "right": null, 
      "right_type": null, 
      "next": 1, 
      "prev": null, 
      "braket_level": 0
    },{
      "left": "@cms3_hierarchy.obj_id", 
      "left_type": "name", 
      "op": "=", 
      "right": "@cms3_object_content.obj_id", 
      "right_type": "name", 
      "next": null, 
      "prev": null, 
      "braket_level": 0
    }],
  "data": null, 
  "metadata": {}, 
  "unipath": "\/cms3_object_content[@cms3_hierarchy.is_deleted=0 and @field_id=506] + cms3_hierarchy[@cms3_hierarchy.obj_id = @cms3_object_content.obj_id]"
}]*/));

		unipath = "/cache(file://./newbilding.json/contents)/ifEmpty(db1/alias('default_Orchard_Framework_ContentItemRecord','cont')[ContentType_id=11]/cahe(file://./newbilding.json))/0";
		System.out.format("%n\033[1m~~~~ %s ~~~~\033[0m%n", unipath);
// 		UniPath.debug_parse = true;
		tree = UniPath.__parseUniPath(unipath.toCharArray(), null, null);
// System.out.println(UniPath.call("toJson", tree));
		assert equ(tree, ms(/*[{}]*/));
		
		unipath = "/db1/table1[id=1]/columns(chunked(table1.Data, 10000, 3000), alias('table1.Id', 'id1'), 'table1.ContentType_id, REPLACE(''abcd)asd'', '')'', ''('')')/asSQLQuery()";
		
		unipath = "/row_data/newbarea/add(', ')/add(row_data/newbadress/regexp_replace('^[0-9]+\\.',''))/remove_end(', ')/remove_start(', ')/ifEmpty('&mdash;')";
		
		unipath = "$_POST/site_map/Недвижимость/premium";
		
		unipath = "db1/users[u_login = /_POST/login and u_password_hash = /_POST/password]/0";
		
		unipath = "db1/orders[u_id = /_SESSION/user/u_id and ord_deleted = 0]";
		
		unipath = "/db1/table1[id = -123]";
		
		unipath = "/db1/table1[]";
		
		unipath = "/db1/table1[1]";
		
		unipath = "/.[1=1 and (DataXML/zemucharea = 'Лазаревский' or DataXML/zemucharea = 'Хостинский') and DataXML/priceuch <= 0]";
		
		unipath = "/.[DataXML/ulstreet='Депутатская','Учительская', \"Лермонтова\", `Пирогова`, 'Грибоедова', 'Дмитриевой', 'Комсомольская', 'Лысая']";
		
		unipath = "_SERVER/DOCUMENT_ROOT/asDirectory()/objs/10597/Pictures/'фото 2-1.JPG'";
		
		unipath = "_SERVER/DOCUMENT_ROOT/asDirectory()/objs/8506/Pictures/`image (3)-7.jpg`/asImageFile()";
		
		unipath = "/_POST/empty_value/ifEmpty('<span class=\"obj-nophoto\">Нет фото</span>')";
		
		unipath = "/row/prd_data_json_encoded/asJSON()/.[./key()/preg_match('image[0-9]$')]";
		
		try {
		unipath = "_SERVER/DOCUMENT_ROOT/asDirectory()/objs/123/Pictures/"+new String("123лаущыцлвв-1.jpg".getBytes("UTF-8"), "windows-1251")+"/asImageFile()/resize(`100`, `100`, `inbox`)/saveAs(`/tmp/"+new String("123лаущыцлвв-1.jpg".getBytes("UTF-8"), "windows-1251")+"`)";
		} catch(UnsupportedEncodingException ex) { /* skip */ }
		
		unipath = "file://./unipath_test.txt";
		
		unipath = "_SERVER/DOCUMENT_ROOT/asDirectory()/objs/123/Pictures/'"+"2,_3,_4этаж[1]-1.JPG"+"'/asImageFile()/resize(`100`, `100`, `fill`)/crop(`100`, `100`)/saveAs(`/tmp/"+"2,_3,_4этаж[1]-1.JPG"+"`)";
		
		/*************** __parseFuncArgs ************** */
		
		String func_string;
		String func_name;
		HashMap<String, Object> args = new HashMap<String, Object>();
		HashMap<String, String> args_types = new HashMap<String, String>();
		
		func_string = "sprintf1(`````````it`s only test %s!```````, /if(['`1'=```1```` and /array(``, ```1]][2))`3```, '\"]')]/```test````,`1`,```2```)/0)";
		System.out.format("%n\033[1m~~~~ %s ~~~~\033[0m%n", func_string); args.clear(); args_types.clear();
// 		UniPath.debug_parse = true;
		func_name = UniPath.__parseFuncArgs(func_string.toCharArray(), args, args_types);
		assert func_name.equals("sprintf1");
// System.out.println(UniPath.call("toJson", args));
		assert equ(args, ms(/*{"0":"``it`s only test %s!","1":"\/if(['`1'=```1```` and \/array(``, ```1]][2))`3```, '\"]')]\/```test````,`1`,```2```)\/0"}*/));
		assert equ(args_types, ms(/*{"0":"string","1":"unipath"}*/));
		
		func_string = "alias('table1', 'tbl1')";
		System.out.format("%n\033[1m~~~~ %s ~~~~\033[0m%n", func_string); args.clear(); args_types.clear();
// 		UniPath.debug_parse = true;
		func_name = UniPath.__parseFuncArgs(func_string.toCharArray(), args, args_types);
		assert func_name.equals("alias");
// System.out.println(UniPath.call("toJson", args));
		assert equ(args, ms(/*{"0":"table1","1":"tbl1"}*/));
		assert equ(args_types, ms(/*{"0":"string","1":"string"}*/));
		
		func_string = "chunked('table1.Data',10000,3000)";
		System.out.format("%n\033[1m~~~~ %s ~~~~\033[0m%n", func_string); args.clear(); args_types.clear();
// 		UniPath.debug_parse = true;
		func_name = UniPath.__parseFuncArgs(func_string.toCharArray(), args, args_types);
		assert func_name.equals("chunked");
// System.out.println(UniPath.call("toJson", args));
		assert equ(args, ms(/*{"0":"table1.Data","1":"10000","2":"3000"}*/));
		assert equ(args_types, ms(/*{"0":"string","1":"number","2":"number"}*/));
		
		func_string = "columns(chunked(table1.Data,10000,3000), alias('table1.Id', 'id1'), 'table1.ContentType_id, REPLACE(''abcd)asd'', '')'', ''('')')";
// 		System.out.format("%n\033[1m~~~~ %s ~~~~\033[0m%n", func_string); args.clear(); args_types.clear();
// 		UniPath.debug_parse = true;
// 		func_name = UniPath.__parseFuncArgs(func_string.toCharArray(), args, args_types);
// 		assert func_name.equals("columns");
// System.out.println(UniPath.call("toJson", args));
// 		assert equ(args, ms(/*{"0":"table1.Data","1":"10000","2":"3000"}*/));
// 		assert equ(args_types, ms(/*{"0":"string","1":"number","2":"number"}*/));
		
		func_string = "join()";
		System.out.format("%n\033[1m~~~~ %s ~~~~\033[0m%n", func_string); args.clear(); args_types.clear();
// 		UniPath.debug_parse = true;
		func_name = UniPath.__parseFuncArgs(func_string.toCharArray(), args, args_types);
		assert func_name.equals("join");
// System.out.println(UniPath.call("toJson", args));
		assert equ(args, ms(/*{}*/));
		assert equ(args_types, ms(/*{}*/));
		
		func_string = "formatQuantity(`тов'ар`, `тов\"ара`,`тов''ар\"\"\"ов`, ````тов''ар\"\"\"ов````, `````тов''ар\"\"\"о``в`````, ```````тов''ар\"\"\"о``в````````, '```товар```', \"товар\")";
		System.out.format("%n\033[1m~~~~ %s ~~~~\033[0m%n", func_string); args.clear(); args_types.clear();
// 		UniPath.debug_parse = true;
		func_name = UniPath.__parseFuncArgs(func_string.toCharArray(), args, args_types);
		assert func_name.equals("formatQuantity");
// System.out.println(UniPath.call("toJson", args));
		assert equ(args, ms(/*{"0":"тов'ар","1":"тов\"ара","2":"тов''ар\"\"\"ов","3":"`тов''ар\"\"\"ов`","4":"``тов''ар\"\"\"о``в``","5":"тов''ар\"\"\"о``в`","6":"```товар```","7":"товар"}*/));
		assert equ(args_types, ms(/*{"0":"string","1":"string","2":"string","3":"string","4":"string","5":"string","6":"string","7":"string"}*/));
		
		func_string = "url(`file://./unipath_test.json`)";
		System.out.format("%n\033[1m~~~~ %s ~~~~\033[0m%n", func_string); args.clear(); args_types.clear();
// 		UniPath.debug_parse = true;
		func_name = UniPath.__parseFuncArgs(func_string.toCharArray(), args, args_types);
		assert func_name.equals("url");
// System.out.println(UniPath.call("toJson", args));
		assert equ(args, ms(/*{"0":"file:\/\/.\/unipath_test.json"}*/));
		assert equ(args_types, ms(/*{"0":"string"}*/));
		
		func_string = "ifEmpty(tmp_dir/asDirectory()/`tm_send.tmp`)";
		System.out.format("%n\033[1m~~~~ %s ~~~~\033[0m%n", func_string); args.clear(); args_types.clear();
// 		UniPath.debug_parse = true;
		func_name = UniPath.__parseFuncArgs(func_string.toCharArray(), args, args_types);
		assert func_name.equals("ifEmpty");
// System.out.println(UniPath.call("toJson", args));
		assert equ(args, ms(/*{"0":"tmp_dir\/asDirectory()\/`tm_send.tmp`"}*/));
		assert equ(args_types, ms(/*{"0":"unipath"}*/));

		func_string = "ifEmpty(db1/alias('default_Orchard_Framework_ContentItemRecord','cont')[ContentType_id=14 and Published=1]+alias('default_Orchard_Framework_ContentItemVersionRecord','vers')[cont.Id = vers.ContentItemRecord_id and Latest=1]\n"+
"	+alias('default_Common_CommonPartRecord', 'comm')[comm.Id = cont.Id]\n"+
"	+alias('default_Orchard_Autoroute_AutoroutePartRecord', 'urls')[urls.Id = vers.Id]\n"+
"	+alias('default_Title_TitlePartRecord', 'titles')[titles.Id = vers.Id]/columns('vers.ContentItemRecord_id, cont.ContentType_id, urls.DisplayAlias, vers.Id as ver_id, comm.OwnerId', chunked(cont.Data, 2000, 3000, `nvarchar(3000)`), chunked(vers.Data, 21000, 3000, 'nvarchar(3000)'), 'titles.Title')/sql_iconv('UTF-8')/iconv('WINDOWS-1251', 'UTF-8'))";
		System.out.format("%n\033[1m~~~~ %s ~~~~\033[0m%n", func_string); args.clear(); args_types.clear();
// 		UniPath.debug_parse = true;
		func_name = UniPath.__parseFuncArgs(func_string.toCharArray(), args, args_types);
		assert func_name.equals("ifEmpty");
// System.out.println(UniPath.call("toJson", args));
		assert equ(args, ms(/*{"0":"db1\/alias('default_Orchard_Framework_ContentItemRecord','cont')[ContentType_id=14andPublished=1]+alias('default_Orchard_Framework_ContentItemVersionRecord','vers')[cont.Id=vers.ContentItemRecord_idandLatest=1]\n\t+alias('default_Common_CommonPartRecord','comm')[comm.Id=cont.Id]\n\t+alias('default_Orchard_Autoroute_AutoroutePartRecord','urls')[urls.Id=vers.Id]\n\t+alias('default_Title_TitlePartRecord','titles')[titles.Id=vers.Id]\/columns('vers.ContentItemRecord_id,cont.ContentType_id,urls.DisplayAlias,vers.Idasver_id,comm.OwnerId',chunked(cont.Data,2000,3000,`nvarchar(3000)`),chunked(vers.Data,21000,3000,'nvarchar(3000)'),'titles.Title')\/sql_iconv('UTF-8')\/iconv('WINDOWS-1251','UTF-8')"}*/));
		assert equ(args_types, ms(/*{"0":"unipath"}*/));

		func_string = "columns(`pb_realty.pbr_id` = `PRIMARY KEY INTEGER`, `pb_realty.pbr_act`=integer, `pb_realty.pbr_closedate` = DATETIME, ads_lifecycle_log.id = ```KEY INTEGER```, ads_lifecycle_log.action, ads_lifecycle_log.date, ```````t_balance_log.id```````=\"KEY\",t_balance_log.value)";
		System.out.format("%n\033[1m~~~~ %s ~~~~\033[0m%n", func_string); args.clear(); args_types.clear();
// 		UniPath.debug_parse = true;
		func_name = UniPath.__parseFuncArgs(func_string.toCharArray(), args, args_types);
		assert func_name.equals("columns");
// System.out.println(UniPath.call("toJson", args));
		assert equ(args, ms(/*{"pb_realty.pbr_id":"PRIMARY KEY INTEGER","ads_lifecycle_log.id":"KEY INTEGER","0":"ads_lifecycle_log.action","1":"ads_lifecycle_log.date","pb_realty.pbr_closedate":"DATETIME","2":"t_balance_log.value","pb_realty.pbr_act":"integer","t_balance_log.id":"KEY"}*/));
		assert equ(args_types, ms(/*{"pb_realty.pbr_id":"string","ads_lifecycle_log.id":"string","0":"unipath","1":"unipath","pb_realty.pbr_closedate":"unipath","2":"unipath","pb_realty.pbr_act":"unipath","t_balance_log.id":"string"}*/));
		
		func_string = "replace_string(`60`=66, `Хостинский`=`Хостинский район`, tmp=./tmp)";
		System.out.format("%n\033[1m~~~~ %s ~~~~\033[0m%n", func_string); args.clear(); args_types.clear();
// 		UniPath.debug_parse = true;
		func_name = UniPath.__parseFuncArgs(func_string.toCharArray(), args, args_types);
		assert func_name.equals("replace_string");
// System.out.println(UniPath.call("toJson", args));
		assert equ(args, ms(/*{"tmp":".\/tmp","60":"66","Хостинский":"Хостинский район"}*/));
		assert equ(args_types, ms(/*{"tmp":"unipath", "60":"number", "Хостинский":"string"}*/));
		
		func_string = "add(row_data/newbadress/regexp_replace('^[0-9]+\\.',''))";
		System.out.format("%n\033[1m~~~~ %s ~~~~\033[0m%n", func_string); args.clear(); args_types.clear();
// 		UniPath.debug_parse = true;
		func_name = UniPath.__parseFuncArgs(func_string.toCharArray(), args, args_types);
		assert func_name.equals("add");
// System.out.println(UniPath.call("toJson", args));
		assert equ(args, ms(/*{"0":"row_data\/newbadress\/regexp_replace('^[0-9]+\\.','')"}*/));
		assert equ(args_types, ms(/*{"0":"unipath"}*/));
		
		func_string = ms(/*array(
		`prd_code1c` = Code, 
		`prd_name` = Description, 
		`parent_code1c` = ./`Родитель--Код`, 
		`prd_deleted` = DeletionMark/replace_string(`ложь`=0,`истина`=1)/php_intval(),
		`prd_url` = array(Description/toURLTranslit(), `_`, Code)/join(),
		`cat_id` = /db_cats/.[cat_code1c=/row/`Родитель--Код`]/first(),
		`cat_code1c` = ./`Родитель--Код`,
		`prd_articul` = ./`Артикул`,
		`prd_data` = array(
			`prd_short_description` = ./`КраткоеОписание`,
			`prd_specifications_raw` = ./`ДополнительноеОписаниеНоменклатуры`
			),
		`prd_price2` = ./'Цены--ИнтернетЦена'/normalize_float(),
        `prd_price3` = ./'Цены--Закупочный'/normalize_float(),
        'prd_price6' = ./'Цены--Оптовая'/normalize_float(), 
        'prd_stock1' = ./'ОстаткиНаСкладе--Сочи'/normalize_float(),
        'prd_stock2' = ./'ВыгружатьНаСайт'/normalize_float(),
        'prd_stock3' = ./'ОстаткиНаСкладе--Краснодар'/normalize_float(),
        'prd_stock4' = ./'ОстаткиНаСкладе--Ростов-на-Дону'/normalize_float(),
        'prd_stock6' = ./'ОстаткиНаСкладе--Москва'/normalize_float(),
        'prd_stock7' = ./'ОстаткиНаСкладе--Пятигорск'/normalize_float()
		")*/);
		System.out.format("%n\033[1m~~~~ %s ~~~~\033[0m%n", func_string); args.clear(); args_types.clear();
// 		UniPath.debug_parse = true;
		func_name = UniPath.__parseFuncArgs(func_string.toCharArray(), args, args_types);
		assert func_name.equals("array") : func_name;
// System.out.println(UniPath.call("toJson", args));
		assert equ(args, ms(/*{"cat_code1c":".\/`Родитель--Код`","prd_url":"array(Description\/toURLTranslit(),`_`,Code)\/join()","prd_code1c":"Code","prd_stock6":".\/'ОстаткиНаСкладе--Москва'\/normalize_float()","prd_deleted":"DeletionMark\/replace_string(`ложь`=0,`истина`=1)\/php_intval()","prd_stock1":".\/'ОстаткиНаСкладе--Сочи'\/normalize_float()","prd_stock2":".\/'ВыгружатьНаСайт'\/normalize_float()","prd_stock3":".\/'ОстаткиНаСкладе--Краснодар'\/normalize_float()","prd_stock4":".\/'ОстаткиНаСкладе--Ростов-на-Дону'\/normalize_float()","prd_price2":".\/'Цены--ИнтернетЦена'\/normalize_float()","prd_articul":".\/`Артикул`","prd_price3":".\/'Цены--Закупочный'\/normalize_float()","prd_name":"Description","cat_id":"\/db_cats\/.[cat_code1c=\/row\/`Родитель--Код`]\/first()","parent_code1c":".\/`Родитель--Код`","prd_price6":".\/'Цены--Оптовая'\/normalize_float()","prd_data":"array(\n\t\t\t`prd_short_description`=.\/`КраткоеОписание`,\n\t\t\t`prd_specifications_raw`=.\/`ДополнительноеОписаниеНоменклатуры`\n\t\t\t)"}*/));
		assert equ(args_types, ms(/*{"cat_code1c":"unipath","prd_url":"unipath","prd_code1c":"unipath","prd_stock6":"unipath","prd_deleted":"unipath","prd_stock1":"unipath","prd_stock2":"unipath","prd_stock3":"unipath","prd_stock4":"unipath","prd_price2":"unipath","prd_articul":"unipath","prd_price3":"unipath","prd_name":"unipath","cat_id":"unipath","parent_code1c":"unipath","prd_price6":"unipath","prd_data":"unipath"}*/));
	}
	
	static public void test_uniPath() {
		UniPath uni;
		Object obj;
		String unipath;
		UniPath.debug = true;
		
		unipath = "fs()";
		System.out.format("%n\033[1m~~~ %s ~~~\033[1m%n", unipath);
		obj = new UniPath(unipath).get();
System.out.println(UniPath.call("toJson", obj));
		assert obj instanceof HashMap;
		
		unipath = "/java.util.HashMap/new()/class";
		System.out.format("%n\033[1m~~~ %s ~~~\033[1m%n", unipath);
		obj = new UniPath(unipath).get();
System.out.println(UniPath.call("toJson", obj));
		assert obj instanceof HashMap;
	}
	
	static public void test_extensions() {
	}
	
	static public String ms() {

		// узнаем номер строки с началом необходимой мультистроки
		Integer line_no = Thread.currentThread().getStackTrace()[2].getLineNumber();
		
		// начнём читать из файла построчно
		Scanner scanr;
		try {
			scanr = new Scanner(new File("UniPath_tests.java"));
		} catch(FileNotFoundException ex) {
			ex.printStackTrace();
			return "";
		}
		
		// пропускаем все строки до нашей строки
		while(scanr.hasNextLine() && --line_no > 0) scanr.nextLine();

		String line = scanr.nextLine();
		
		// одна строка?
		if(line.indexOf("ms(/*") > -1 && line.indexOf("*/", line.indexOf("ms(/*")) > -1)
			return line.substring(line.indexOf("ms(/*")+5, line.indexOf("*/", line.indexOf("ms(/*")));
		
		// начинаем извлекать мултистроку
		StringBuilder sb = new StringBuilder();
		sb.append(line.substring(line.indexOf("ms(/*")+5));
		sb.append('\n');
		
		// извлекаем тело
		while(scanr.hasNextLine()) {
			line = scanr.nextLine();
			if(line.indexOf("*/") > -1) break;
			sb.append(line);
			sb.append('\n');
		}
		
		// извлекаем конец мультистроки
		sb.append(line.substring(0, line.indexOf("*/")));

// System.out.println(sb.toString());
		return sb.toString();
	}

	static public Boolean equ(Object left, String right) {
		String left_as_json = ((String) UniPath.call("toJson", left)).replace(" ", "").replace("\t", "");
		if(left_as_json.equals(right = right.replace(" ", "").replace("\t", ""))) return true;

		// иначе начинаем искать начало несовпадения
		for(int i = left_as_json.length(); i >= 0; i--) {
			if(left_as_json.substring(0, i)
			.equals(right.substring(0, i > right.length() ? right.length() : i))) {
				System.out.format("==== left ====%n%s%n==== right ==== %n%s%n=============%n", 
				left_as_json.substring(i, left_as_json.length()), 
				right.substring(i, right.length()));
				break;
			}
		}
		
		return false;
	}
}