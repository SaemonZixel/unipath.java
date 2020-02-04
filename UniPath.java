import java.util.*;
import java.lang.reflect.Method;
import java.lang.reflect.Field;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 *  UniPath - XPath like access to DataBase, Files, XML and any other data from Java
 *  
 *  @version  0.9
 *  @author   Saemon Zixel <saemonzixel@gmail.com>
 *  @link     https://github.com/SaemonZixel/unipath.java
 *
 *	UniversalPath (UniPath.java) - универсальный доступ к любым ресурсам
 *  Задумывался как простой, компактный и удобный интерфейс ко всему. Идеологически похож на jQuery и XPath.
 *  Позваляет читать и манипулировать, в том числе и менять, как локальные переменные внутри программы,
 *  так и в файлы на диске, таблицы в базе данных, удалённые ресурсы и даже менять на удалённом сервере 
 *  параметры запущенного приложения или считать запись access.log по определённой дате и подстроке UserAgent и т.д.
 *  Но всё это в светлом будущем:) Сейчас реализованна только маленькая часть.
 *
 *
 *  @license  MIT
 *
 *  Copyright (c) 2019-2020 Saemon Zixel
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy of this software 
 *  and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

class UniPath {
	static public Boolean debug_parse = false;
	static public Boolean debug = false;
	
	static public Integer __prt_cnt = 100000; // лимит интераций циклов, защитный счётчик от бесконечного зацикливания
	static public Integer __optimize = 1; // 0 - off; 1 - optimeze: /abc, ./abc, ./`abc`, .
	static public Object __benchmark; // для статистики скорости обработки unipath запросов

	// дополнительные функции
	static public HashMap<String, Object> __extensions = new HashMap<String, Object>();
	static {
		__extensions.put("toJson", UniPath.class);
// 		__extensions.put("fs", UniPath.class);
	}
	
	public ArrayList<Node> tree; // текущее разобранное дерево unipath_query
// 	public Boolean _assign_mode = false; // режим присвоения значения
	
	static class Node /* implements ListIterator? */ {
		public String name;
		public ArrayList<Expr> filter;
		public Object data;
		public HashMap<String, Object> metadata;
		public String unipath;
		
		public Node() {
			metadata = new HashMap<String, Object>();
		}
		
		public Node(String _name, Object _data) {
			name = _name;
			data = _data;
			metadata = new HashMap<String, Object>();
			metadata.put("0", _data == null ? "null" : _data.getClass().getName());
			unipath = "";
		}
		
		public Node(String _name, Object _data, HashMap<String, Object> _metadata, String _unipath) {
			name = _name;
			data = _data;
			metadata = _metadata;
			unipath = _unipath;
		}
	
		public Boolean eval(ArrayList<Node> tree, Integer curr_lv) {
			return false; // стандартная обработка
		}
	}
	
	static class Expr {
		public Object left;
		public String left_type;
		public String op;
		public Object right;
		public String right_type;
		public Integer next;
		public Integer prev;
		public Integer braket_level = 0;

		public Expr() {}
		
		public Expr(int _next) {
			next = _next;
		}
		
		public Expr(Integer _next, Integer _bracket_level) {
			next = _next;
		}
		
		public Expr(Integer _next, Object _left, String _left_type, String _op, Integer _bracket_level) {
			next = _next;
			left = _left;
			left_type = _left_type;
			op = _op;
			braket_level = _bracket_level;
		}
		
		public Expr(Integer _next, Object _left, String _left_type, String _op, Object _right, String _right_type, Integer _bracket_level) {
			next = _next;
			left = _left;
			left_type = _left_type;
			op = _op;
			right = _right;
			right_type = _right_type;
			braket_level = _bracket_level;
		}
		
		public Boolean op_is_any(String op1, String op2, String op3, String op4, String op5) {
			if(op == null) return false;
			return op.equals(op1) || op.equals(op2) || op.equals(op3) || op.equals(op4) || op.equals(op5);
		}
		
		public Boolean op_is_any(String op1, String op2, String op3, String op4) {
			if(op == null) return false;
			return op.equals(op1) || op.equals(op2) || op.equals(op3) || op.equals(op4);
		}
		
		public Boolean op_is_any(String op1, String op2, String op3, String op4, String op5, String op6, String op7, String op8, String op9, String op10, String op11, String op12, String op13) {
			if(op == null) return false;
			return op.equals(op1) || op.equals(op2) || op.equals(op3) || op.equals(op4) || op.equals(op5) || op.equals(op6) || op.equals(op7) || op.equals(op8) || op.equals(op9) || op.equals(op10) || op.equals(op11) || op.equals(op12) || op.equals(op13);
		}
		
		public Boolean op_is_any(String op1, String op2, String op3, String op4, String op5, String op6, String op7, String op8, String op9, String op10, String op11, String op12, String op13, String op14) {
			if(op == null) return false;
			return op.equals(op1) || op.equals(op2) || op.equals(op3) || op.equals(op4) || op.equals(op5) || op.equals(op6) || op.equals(op7) || op.equals(op8) || op.equals(op9) || op.equals(op10) || op.equals(op11) || op.equals(op12) || op.equals(op13) || op.equals(op14);
		}
	}

	public UniPath() {
		tree = new ArrayList<Node>();
		tree.add(new Node());
	}
	
	public UniPath(String unipath_query) {
		tree = __parseUniPath(unipath_query.toCharArray(), null, null);
		__evalUniPath(tree);
	}

	public UniPath(String unipath_query, Object arg1) {
		tree = __parseUniPath(unipath_query.toCharArray(), new Object[]{ arg1 }, null);
		__evalUniPath(tree);
	}
	
	public Object get() {
		return tree.get(tree.size()-1).data;
	}
	
	public Object get(String unipath_query) {
		tree = __parseUniPath(
			unipath_query.toCharArray(), 
			tree.get(tree.size()-1).data, 
			tree.get(tree.size()-1).metadata
		);
		__evalUniPath(tree);
		return tree.get(tree.size()-1).data;
	}
	
// 	public Object get(String unipath_query, Object arg1) {
// 		tree = __parseUniPath(unipath_query.toCharArray(), new Object[]{ arg1 }, null);
// 		__evalUniPath(tree);
// 		return tree.get(tree.size()-1).data;
// 	}
	
	public Boolean set(String unipath_query, Object set_value) {
		return false;
	}
	
	// регистрация/добавление расширений
	static public Boolean register(Object obj, String meth_name, String call_type) {
		UniPath.__extensions.put(meth_name, obj);
		return true;
	}

	static public Object call(String func_name, Object arg1) {
		ArrayList<UniPath.Node> tree_call = new ArrayList<UniPath.Node>();
		tree_call.add(new UniPath.Node("?start_data?", arg1));
		tree_call.add(new UniPath.Node(func_name, null));
		
		try {
			UniPath.class
				.getMethod(func_name, ArrayList.class, Integer.class)
				.invoke(null, new Object[]{ tree_call, 1 });
			return tree_call.get(1).data;
		} catch (InvocationTargetException ex) {
			throw new RuntimeException(ex);
		} catch (NoSuchMethodException ex) {
			throw new RuntimeException(ex);
		} catch (IllegalAccessException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	// главная функция (сердце UniPath)
	public Boolean __evalUniPath(ArrayList<Node> tree) {
System.out.println(UniPath.call("toJson", tree));

		if(tree.size() > 100)
			throw new RuntimeException("Too many steps! - "+tree.size());

		Boolean current_tree_node_already_evaluted = false;
		
		// Главный цикл
		Integer lv;
		for(lv = 1; lv < tree.size(); lv++) {
			if(UniPath.debug) System.out.format("%n**** %s ****%n", tree.get(lv).unipath);
		
			String name = tree.get(lv).name.toString();
			ArrayList<UniPath.Expr> filter = tree.get(lv).filter == null 
				? new ArrayList<UniPath.Expr>() 
				: tree.get(lv).filter;
			
			// *** CustomNode? ***
			if(lv > 0 && tree.get(lv-1).getClass() != UniPath.Node.class) {
			
				// если ответ пришёл нормальный, то переходем к следующему узлу
				if(tree.get(lv-1).eval(tree, lv) == true) {
					continue; 
				} 
				
				// иначе обрабатываем самостоятельно...
			}
			
			// prev_data_type должен быть обязательно
			if(lv > 0 && (tree.get(lv-1).metadata == null || tree.get(lv-1).metadata.containsKey("0") == false) && current_tree_node_already_evaluted == false)
				throw new RuntimeException("no data_type set on step #"+lv+"! "+tree.get(lv-1).toString());
				
			String prev_data_type = (String) tree.get(lv-1).metadata.get("0");
System.out.format("prev_data_type = %s%n", prev_data_type);

			// /Class/... если начинается с названия класса
			if(lv == 1 && name.indexOf('(') == -1) {
// 				java.lang.reflect.Method meth_findLoadedClass = ClassLoader.class.getDeclaredMethod("findLoadedClass", new Class[] { String.class });
// 				meth_findLoadedClass.setAccessible(true);
// 				Object found_class = meth_findLoadedClass.invoke(
// 					Thread.currentThread().getContextClassLoader(), 
// 					name);
				try {
					Class found_class = Class.forName(name);
					tree.get(lv).data = found_class;
					tree.get(lv).metadata.put("0", "Class");
					tree.get(lv).metadata.put("key()", name);
				}
				catch(ClassNotFoundException ex) {
					if(UniPath.debug) ex.printStackTrace();
					tree.get(lv).data = null;
					tree.get(lv).metadata.put("0", "null");
					tree.get(lv).metadata.put("key()", name);
				}
			}
			
			// Class.<name>()
			else if(name.indexOf('(') > 0 && prev_data_type.equals("Class")) {
				HashMap<String, Object> args = new HashMap<String, Object>();
				HashMap<String, String> args_types = new HashMap<String, String>();
				UniPath.__parseFuncArgs(name.toCharArray(), args, args_types);
				
				// TODO $args ...
			
				Class loaded_class = (Class) tree.get(lv-1).data;
				String func_name = name.substring(0, name.indexOf('('));
				
				try {
					// создать объект?
					if(func_name.equals(loaded_class.getName()) || func_name.equals("new")) {
						tree.get(lv).data = ((Class) tree.get(lv-1).data).getConstructor().newInstance();
					} 
					
					// или вызвать статичный метод?
					else {
						tree.get(lv).data = loaded_class
							.getDeclaredMethod(func_name, new Class[]{})
							.invoke(null, new Object[]{});
					}
				} catch (NoClassDefFoundError ex) {
					ex.printStackTrace();
					tree.get(lv).data = ex;
				} catch (InstantiationException ex) {
					ex.printStackTrace();
					tree.get(lv).data = ex;
				} catch (InvocationTargetException ex) {
					ex.printStackTrace();
					tree.get(lv).data = ex;
				} catch (NoSuchMethodException ex) {
					ex.printStackTrace();
					tree.get(lv).data = ex;
				} catch (IllegalAccessException ex) {
					ex.printStackTrace();
					tree.get(lv).data = ex;
				}
				
				tree.get(lv).metadata.put("0", tree.get(lv).data.getClass().getName());
			}
			
			// __extensions[<name>]()
			else if(name.indexOf('(') > 0 && __extensions.containsKey(name.substring(0, name.indexOf('(')))) {
				try {
					UniPath.class
						.getDeclaredMethod(name.substring(0, name.indexOf('(')), new Class[]{ ArrayList.class, Integer.class })
						.invoke(null, new Object[]{ tree, lv });
				} catch (InvocationTargetException ex) {
					ex.printStackTrace();
					tree.get(lv).data = ex;
					tree.get(lv).metadata.put("0", "Exception");
				} catch (NoSuchMethodException ex) {
					ex.printStackTrace();
					tree.get(lv).data = ex;
					tree.get(lv).metadata.put("0", "Exception");
				} catch (IllegalAccessException ex) {
					ex.printStackTrace();
					tree.get(lv).data = ex;
					tree.get(lv).metadata.put("0", "Exception");
				}
			}
			
			// если не понятно что делать, тогда просто копируем данные
			else {
				System.out.format("UniPath.__evalUniPath: unknown - %s (skip)", name);
			
				tree.get(lv).data = lv > 0 ? tree.get(lv-1).data : new ArrayList<>();
				if(lv > 0 && tree.get(lv-1).metadata != null)
					tree.get(lv).metadata = tree.get(lv-1).metadata;
				else {
					tree.get(lv).metadata = new HashMap<String, Object>();
					tree.get(lv).metadata.put("0", "array");
				}
				
				// специально пометим, что это копия предыдущего шага
				if(tree.get(lv).metadata.containsKey("this_step_is_copy_of_step") == false)
					tree.get(lv).metadata.put("this_step_is_copy_of_step", lv-1);
			};
			
		} // for(lv = ...)
		
		// закончился unipath?
		if(tree.size() < lv+1) { 
		
/*	if(!empty($GLOBALS['unipath_debug'])) { 
		echo "<br>------------<br>\n";
		if(isset($tree[$lv-1]['data']) and is_array($tree[$lv-1]['data']) and isset($tree[$lv-1]['data']['GLOBALS'], $tree[$lv-1]['data']['GLOBALS']['GLOBALS'], $tree[$lv-1]['data']['GLOBALS']['GLOBALS']['GLOBALS'])) 
			print_r(array_merge($tree[$lv-1], array('data' => '*** $GLOBALS ***')));
		else
			print_r($tree[$lv-1]);
	}*/
			return true;
		} 
		
		// обработка xpath была прервана?
		System.out.format("UniPath.__evalUniPath: evaluation interrupted on step #%d! (%s)%n", lv, tree.get(lv-1).unipath);
		
		return false;
	}
	
	// парсит unipath запрос на отдельные шаги (аргументы функций не парсит)
	static public ArrayList<Node> __parseUniPath(char[] xpath, Object start_data, HashMap<String, Object> start_metadata) {
		
		if(UniPath.debug_parse) System.out.println("Parsing - " + new String(xpath));
		
		ArrayList<Node> tree = new ArrayList<Node>();
		
		// абсалютный путь - стартовые данные это $GLOBALS
		// для относительного, если не передали стартовые данные, то тоже $GLOBALS
		if(xpath[0] == '/' || start_data == null && start_metadata == null) {
			tree.add(new Node("?start_data?", null, new HashMap<String, Object>(), ""));
			tree.get(0).metadata.put("0", "null");
		}
		
			
		// относительный путь - стартовые данные берём, которые передали
		else {
			tree.add(
				new Node("?start_data?", 
					start_data, 
					start_metadata == null ? new HashMap<String, Object>() : start_metadata, 
					"")
			);
			if(tree.get(0).metadata.containsKey("0") == false)
				tree.get(0).metadata.put("0", start_data.getClass().getName());
		}
		
		// если первым указан протокол, то распарсим его заранее
		/* if(sscanf($xpath, '%[qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM_0123456789]:%2[/]', $scheme, $trailing_component) == 2) {
			$scheme = strtolower($scheme);
			switch($scheme) {
				case 'file':
					$tree[] = array('name' => 'fs()', 'data' => 'file://', 'data_type' => 'string/local-filesystem', 'data_tracking' => array('key()' => 'file://'), 'unipath' => 'file://'); 
					break;
				case 'http':
					$url = strpos($xpath, "#/") > 0 ? substr($xpath, 0, strpos($xpath, "#/")) : $xpath;
					$tree[] = array('name' => $url, 'unipath' => $url, 'data' => $url, 'data_type' => 'string/url', 'data_tracking' => array('key()' => $url));
					break;
				default:
					$tree[] = array('name' => "$scheme://", 'unipath' => "$scheme://"); 
			}
// 			$tree[] = array('name' => null);
			$p = strlen($scheme) + 3;
		}  */
		
		// если относительный путь, то создадим сразу новый узел
		if(xpath[0] != '/') {
			tree.add(new Node());
		} 

		Integer name_suffix_len = 0;
		Integer p = 0;
		
		ArrayList<Expr> filter;
		
		// __prt_cnt - защита от зацикливания
		for(int prt_cnt = UniPath.__prt_cnt; p < xpath.length && prt_cnt > 0; prt_cnt--) {
if(UniPath.debug_parse) System.out.format("0:p => %s%n", new String(xpath, 0, p));

			// новая ось
			if(xpath[p] == '/') {
			
				// укажем путь предыдушего уровня пути
				if(tree.isEmpty() == false)
					tree.get(tree.size()-1).unipath = new String(xpath, 0, p);
					
				tree.add(new Node());
				name_suffix_len = 0;
				p++;
					
				continue;
			} 
			
			// либо разделители '+-,' -> создаём новую ось (синт.сахар)
			if("+-,".indexOf(xpath[p]) > -1) {
				
				// укажем путь предыдушего уровня пути
				if(tree.isEmpty() == false)
					tree.get(tree.size()-1).unipath = new String(xpath, 0, p);
				
				tree.add(new Node());
				
				if(xpath.length > p && "+-,".indexOf(xpath[p+1]) > -1) {
					name_suffix_len = 2;
					p += 2;
				}
				else {
					name_suffix_len = 1;
					p += 1;
				}
			}
			
			// названия поля/оси
			// strpos('qwertyuiopasdfghjklzxcvbnm_*@0123456789.$', strtolower(xpath[p]))
			if("\\|/,+[](){}<>?!~`'\";#^&=- \n\t\r".indexOf(xpath[p]) == -1) {
				
				// выделяем буквенную часть
				int start_p = p;
				while(p < xpath.length && "\\|/,+[](){}<>?!~`'\";#^&= \n\t\r".indexOf(xpath[p]) == -1) 
					p += 1;
					
				// выделяем generic если есть
				if(p < xpath.length && xpath[p] == '<') {
					int type_lv = 1;
					p++;
					for(;xpath.length > p && type_lv > 0; p++) {
						if(xpath[p] == '>') type_lv--;
						else if(xpath[p] == '<') type_lv++;
					}
					
					// закончилась строка, а generic-чать не закрылась?
					if(type_lv > 0)
						throw new RuntimeException("Not found end '>' symbol!");
				}
				
if(UniPath.debug_parse) System.out.format("fieldname detected: p,len = %s%n", new String(xpath, start_p, p-start_p));
				// может это function()?
				if(xpath.length > p && xpath[p] == '(') {
					
					int[] tmp_stack = new int[32]; int tmp_num = 0; tmp_stack[0] = 12;
					while(p < xpath.length) {
					
						// ```````...
						if(tmp_stack[tmp_num] > 10 && xpath.length > p+6 
						&& xpath[p] == '`' && xpath[p+1] == '`' && xpath[p+2] == '`' 
						&& xpath[p+3] == '`' && xpath[p+4] == '`' && xpath[p+5] == '`' && xpath[p+6] == '`') { 
							p += 6;
							tmp_stack[++tmp_num] = 5;
						}
						
						// ...```````
						else if(xpath.length > p+6 && tmp_stack[tmp_num] == 5 && xpath[p] == '`' && xpath[p+1] == '`' && xpath[p+2] == '`' && xpath[p+3] == '`' && xpath[p+4] == '`' && xpath[p+5] == '`' && xpath[p+6] == '`') {
							if(xpath.length > p+7 && xpath[p+7] == '`') { p++; continue; } // экранировання ковычка
							p += 6;
							tmp_num--;
						}
					
						// ...```
						else if(tmp_stack[tmp_num] == 4 && xpath.length > p+2 && xpath[p] == '`' && xpath[p+1] == '`' && xpath[p+2] == '`') {
							if(xpath.length > p+3 && xpath[p+3] == '`') { p++; continue; }
							p += 2;
							tmp_num--;
						}
						
						// ```...
						else if(tmp_stack[tmp_num] > 10 && xpath.length > p+2 && xpath[p] == '`' && xpath[p+1] == '`' && xpath[p+2] == '`') { 
							p += 2;
							tmp_stack[++tmp_num] = 4;
						}
						
						// `...`
						else if(xpath[p] == '`' && tmp_stack[tmp_num] == 3) tmp_num--;
						else if(xpath[p] == '`' && tmp_stack[tmp_num] > 10) tmp_stack[++tmp_num] = 3;
						
						else if(xpath[p] == '(' && tmp_stack[tmp_num] > 10) tmp_stack[++tmp_num] = 12;
						else if(xpath[p] == ')' && tmp_num == 1) { p++; break; }
						else if(xpath[p] == ')' && tmp_stack[tmp_num] == 12) tmp_num--;
						else if(xpath[p] == '\'' && tmp_stack[tmp_num] == 1) tmp_num--;
						else if(xpath[p] == '\'' && tmp_stack[tmp_num] > 10) tmp_stack[++tmp_num] = 1;
						else if(xpath[p] == '"' && tmp_stack[tmp_num] == 2) tmp_num--;
						else if(xpath[p] == '"' && tmp_stack[tmp_num] > 10) tmp_stack[++tmp_num] = 2;
						
						
if(UniPath.debug_parse) System.out.format("function_parse - %c%s tmp_stack[%d] = %d %n", xpath[p], new String(new char[tmp_num*3]).replace('\0', ' '), tmp_num, tmp_stack[tmp_num]);
						p++;
					}
					
/*					$inner_brakets = 1; $inner_string = false;
					while($inner_brakets > 0 and isset(xpath[p])) { 
if(!empty($GLOBALS['unipath_debug_parse'])) var_dump("braket_mode: xpath[p] = {xpath[p]}, inner_string =  $inner_string");
						$tree[count($tree)-1]['name'.$suffix] .= $xpath[$p++];
						if(!isset(xpath[p]))
							trigger_error("UniPath: __uni_parseUniPath(): Not found close braket in $xpath!", E_USER_ERROR);
						
						if(xpath[p] == "'" and $inner_string == false)  $inner_string = "'";
						elseif(xpath[p] == "'" and $inner_string == "'")  $inner_string = false;
						elseif(xpath[p] == "`" and $inner_string == false)  $inner_string = "`";
						elseif(xpath[p] == "`" and $inner_string == "`")  $inner_string = false;
						elseif(xpath[p] == '(' and !$inner_string) $inner_brakets++;
						elseif(xpath[p] == ')' and !$inner_string) $inner_brakets--;
					}
					$tree[count($tree)-1]['name'.$suffix] .= $xpath[$p++]; */
					
if(UniPath.debug_parse) System.out.println("braket_mode_result = "+new String(xpath, start_p, p - start_p));
				}
				
				tree.get(tree.size()-1).name = new String(xpath, start_p - name_suffix_len, p - start_p + name_suffix_len);
				name_suffix_len = 0;
			
				continue;
			}
			
			// [] фильтрация
			if(xpath[p] == '[') {
if(UniPath.debug_parse) System.out.format("filtration start - %s%n", p);
				p++; // [

				// разбираем фильтр
				filter = tree.get(tree.size()-1).filter = new ArrayList<Expr>();
				filter.add(new Expr(1));
				filter.add(new Expr());
				
				Integer next_expr_num = 2;
				Integer expr = 1;
				String expr_key = "left";
				Integer curr_braket_level = 0;
				String op;
				while(p < xpath.length && xpath[p] != ']') {
if(UniPath.debug_parse) System.out.format("--- %d --- (op: %s, bl: %d)%n", expr, filter.get(expr).op, curr_braket_level);
					while(" \n\t".indexOf(xpath[p]) > -1 && p+1 < xpath.length) p++;
if(UniPath.debug_parse) System.out.println(new String(xpath, 0, p));
					// до конца фильтрации были пробелы?
					if(xpath[p] == ']') continue;
					
					// (
					if(xpath[p] == '(') {
if(UniPath.debug_parse) System.out.format("filter_braket_opened - %d%n", p);

						// углубляемся в групировку
						curr_braket_level += 1;
						
						// создаём новый узел выражения и вклиниваем его в цепочку
						Integer old_expr = expr;
						expr = next_expr_num++;
						filter.add(new Expr(old_expr, null, null, null, curr_braket_level));
						filter.get(old_expr).right = expr;
						filter.get(old_expr).right_type = "expr";
						expr_key = "left";
if(UniPath.debug_parse) System.out.format("inserted_expr - %d%n", expr);
						
						// корректируем порядок выполнения
						for(Expr item : filter)
							if(item.next == old_expr) {
if(UniPath.debug_parse) System.out.format("  go-up-backpatch (%d, %s)%n", item.next, item.op);
							item.next = expr;
							break;
						}
						
						// если мы вклиниваемся, то и начало цепочки корректируем
						if(filter.get(0).next == old_expr)
							filter.get(0).next = expr;
						
						p++;
						continue;
					}
					
					// )
					if(xpath[p] == ')') {
if(UniPath.debug_parse) System.out.format("filter_braket_closed - %d%n", p);

						// всплываем из групировки
						curr_braket_level -= 1;
						
						p++;
						continue;
					}
					
					// +,-
					if(xpath[p] == '+' || (xpath[p] == '-' && xpath.length > p+1) && "0123456789".indexOf(xpath[p+1]) < 0) {
						op = new String(xpath, p++, 1);
						
						if(expr_key.equals("left")) {
							filter.get(expr).op = op;
							expr_key = "right";
							continue;
						}

						// поднимемся наверх в поисках того, у кого мы сможе отобрать правую часть
						Integer old_expr = expr;
						while(filter.get(old_expr).op_is_any("*","div","mod","+","-") && filter.get(old_expr).braket_level == 0)
							if( filter.get(old_expr).next == null ) break;
							else old_expr = filter.get(old_expr).next;

						// прикрепляемся справа (продолжаем цепочку)
						if(filter.get(old_expr).op_is_any("*","div","mod","+","-") && filter.get(old_expr).braket_level == 0) {
							expr = next_expr_num++;
							filter.add(new Expr(filter.get(old_expr).next, old_expr, "expr", op, curr_braket_level));
							filter.get(old_expr).next = expr;
						} 
						
						// отбираем правую часть
						else {
							expr = next_expr_num++;

							// пред. звено -> [мы]
							if(filter.get(old_expr).left_type.equals("expr")) {
								filter.get((Integer) filter.get(old_expr).left).next = expr;
							}
							
							// [мы] -> (старое) теущее звено
							filter.add(new Expr(old_expr, filter.get(old_expr).right, filter.get(old_expr).right_type, op, curr_braket_level));
							
							// right = [мы]
							filter.get(old_expr).right = expr;
							filter.get(old_expr).right_type = "expr";
							
							// если мы вклиниваемся, то и начало цепочки корректируем
							if(filter.get(0).next == old_expr)
								filter.get(0).next = expr;
						}
						
						continue;
					};
					
					// *,div,mod
					if(xpath[p] == '*' 
						|| ((xpath.length > p+3 && xpath[p+3] == ' ') && (
						("dD".indexOf(xpath[p]) > -1 && "iI".indexOf(xpath[p+1]) > -1 && "vV".indexOf(xpath[p+2]) > -1)
						|| ("mM".indexOf(xpath[p]) > -1 && "oO".indexOf(xpath[p+1]) > -1 && "dD".indexOf(xpath[p+2]) > -1))
					))
					{
						op = xpath[p] == '*' ? "*" : new String(xpath, p, 3).toLowerCase();
						p += xpath[p] == '*' ? 1 : 3;
if(UniPath.debug_parse) System.out.format("found %s %n", op);
						if(expr_key.equals("left")) {
							filter.get(expr).op = op;
							expr_key = "right";
							continue;
						}
						
						Integer old_expr;
						if(expr_key.equals("right")) {

							// поднимемся наверх в поисках того, у кого мы сможе отобрать правую часть
							old_expr = expr;
if(UniPath.debug_parse) System.out.format("  go-up-start (%d, %s)%n", old_expr, filter.get(old_expr).op);
							while(filter.get(old_expr).op_is_any("*","/","div","mod") && filter.get(old_expr).braket_level == curr_braket_level)
								if(filter.get(old_expr).next == null) break;
								else old_expr = filter.get(old_expr).next;

							// прикрепляемся справа (продолжаем цепочку)
							if(filter.get(old_expr).op_is_any("*","/","div","mod") || filter.get(old_expr).braket_level != curr_braket_level) {
if(UniPath.debug_parse) System.out.format("  go-up-continue (%d, %s)%n", old_expr, filter.get(old_expr).op);
								expr = next_expr_num++;
								filter.add(new Expr(filter.get(old_expr).next, old_expr, "expr", op, curr_braket_level));
								filter.get(old_expr).next = expr;
							} 
							
							// отбираем правую часть (вклиниваемся между звеньями)
							else {
								expr = next_expr_num++;
if(UniPath.debug_parse) System.out.format("  go-up-insert (%d, %s)%n", old_expr, filter.get(old_expr).op);
								// пред. звено -> [мы]
								if(filter.get(old_expr).right_type == null)
									{ /* do_nothing */ }
								else
								if(filter.get(old_expr).right_type.equals("expr"))
									filter.get((Integer) filter.get(old_expr).right).next = expr;
								else
								if(filter.get(old_expr).left_type.equals("expr"))
									filter.get((Integer) filter.get(old_expr).left).next = expr;
									
								// [мы] -> (старое) теущее звено
								filter.add(new Expr(old_expr, filter.get(old_expr).right, filter.get(old_expr).right_type, op, curr_braket_level));
								
								// right = [мы]
								filter.get(old_expr).right = expr;
								filter.get(old_expr).right_type = "expr";

								// если мы вклиниваемся, то и начало цепочки корректируем
								if(filter.get(0).next == old_expr && filter.get(expr).left_type.equals("expr") == false)
									filter.get(0).next = expr;
							}
						
							continue;
						}
					};
					
					// and, or
					if((xpath.length > p+4 && "aA".indexOf(xpath[p]) > -1 && "nN".indexOf(xpath[p+1])> -1 && "dD".indexOf(xpath[p+2]) > -1 && xpath[p+3] == ' ') 
					|| (xpath.length > p+3 && "oO".indexOf(xpath[p]) > -1 && "rR".indexOf(xpath[p+1]) > -1 && xpath[p+2] == ' ')) {
						op = (xpath[p] == 'a' || xpath[p] == 'A') ? "and" : "or";
						p += (xpath[p] == 'a' || xpath[p] == 'A') ? 3 : 2;
if(UniPath.debug_parse) System.out.format("AND/OR detected! - %s%n", op);

						if(expr_key.equals("left")) {
							filter.get(expr).op = op;
							expr_key = "right";
							continue;
						}

if(UniPath.debug_parse) System.out.format("  go-up-start (%d, %s)%n", expr, filter.get(expr).op);
						// поднимемся наверх в поисках того, у кого мы сможе отобрать правую часть
						Integer old_expr = expr;
						while(filter.get(old_expr).op_is_any("*","div","mod", "+","-", "=","<",">","<=",">=", "<>", "!=", "and", "or") && filter.get(old_expr).braket_level == curr_braket_level)
							if(filter.get(old_expr).next == null) break;
							else old_expr = filter.get(old_expr).next;

						// прикрепляемся справа (продолжаем цепочку)
						if(filter.get(old_expr).op_is_any("*","div","mod", "+","-", "=","<",">","<=",">=", "<>", "!=", "and", "or") && filter.get(old_expr).braket_level == curr_braket_level) {
if(UniPath.debug_parse) System.out.format("  go-up-continue (%d, %s)%n", old_expr, filter.get(old_expr).op);
							expr = next_expr_num++;
							filter.add(new Expr(filter.get(old_expr).next, old_expr, "expr", op, curr_braket_level));
							filter.get(old_expr).next = expr;
						} 
						
						// отбираем правую часть
						else {
if(UniPath.debug_parse) System.out.format("  go-up-insert (%d, %s)%n", old_expr, filter.get(old_expr).op);
							expr = next_expr_num++;
//var_dump("$expr: $old_expr");
							// пред. звено -> [мы]
							if(filter.get(old_expr).right_type.equals("expr"))
								filter.get((Integer) filter.get(old_expr).right).next = expr;
							else if(filter.get(old_expr).left_type.equals("expr"))
									filter.get((Integer) filter.get(old_expr).left).next = expr;
							
							// [мы] -> (старое) теущее звено
							filter.add(new Expr(old_expr, filter.get(old_expr).right, filter.get(old_expr).right_type, op, curr_braket_level));
							
							// right = [мы]
							filter.get(old_expr).right = expr;
							filter.get(old_expr).right_type = "expr";
						}
						
						continue;
					};
					
					// =, >, <, <=, >=, <>, !=
					if(xpath[p] == '=' || xpath[p] == '>' || xpath[p] == '<' || xpath[p] == '!') {
						if(xpath.length > p && "<>=".indexOf(xpath[p+1]) > -1)
							op = new String(xpath, (p+=2)-2, 2);
						else 
							op = new String(xpath, p++, 1);
							
						// стандартное начало выражения
						if(expr_key.equals("left")) {
							filter.get(expr).op = op;
							expr_key = "right";
							continue;
						}
						
						// продолжение цепочки выражения
						if(expr_key.equals("right")) {
							
if(UniPath.debug_parse) System.out.format("  go-up-start (%d, %s)%n", expr, filter.get(expr).op);
							// поднимемся наверх в поисках того, у кого мы сможе отобрать правую часть
							Integer old_expr = expr;
							while(filter.get(old_expr).op_is_any("*","/","div","mod", "+","-", "=","<",">","<=",">=", "<>", "!=") || filter.get(old_expr).braket_level != curr_braket_level)
								if(filter.get(old_expr).next == null) break;
								else old_expr = filter.get(old_expr).next;

							// прикрепляемся справа (продолжаем цепочку)
							if(filter.get(old_expr).op_is_any("*","/","div","mod", "+","-", "=","<",">","<=",">=", "<>", "!=") || filter.get(old_expr).braket_level != curr_braket_level) {
if(UniPath.debug_parse) System.out.format("  go-up-continue (%d, %s)%n", old_expr, filter.get(old_expr).op);
								expr = next_expr_num++;
								filter.add(new Expr(filter.get(old_expr).next, old_expr, "expr", op, curr_braket_level));
								filter.get(old_expr).next = expr;
							} 
							
							// отбираем правую часть (вклиниваемся между звеньями)
							else {
if(UniPath.debug_parse) System.out.format("  go-up-insert (%d, %s)%n", old_expr, filter.get(old_expr).op);
								expr = next_expr_num++;
								
								// пред. звено -> [мы]
								for(Expr item : filter)
									if(item.next == old_expr) {
if(UniPath.debug_parse) System.out.format("  go-up-backpatch (%d, %s)%n", item.next, item.op);

										item.next = expr;
										break;
									}
								
								// [мы] -> (старое) теущее звено
								filter.add(new Expr(old_expr, filter.get(old_expr).right, filter.get(old_expr).right_type, op, curr_braket_level));
								
								// right = [мы]
								filter.get(old_expr).right = expr;
								filter.get(old_expr).right_type = "expr";

								// если мы вклиниваемся, то и начало цепочки корректируем
								if(filter.get(0).next == old_expr && filter.get(expr).left_type.equals("expr") == false)
									filter.get(0).next = expr;
// if(UniPath.debug_parse) System.out.format("old_expr.next = %d%n", filter.get(old_expr).next);
							}
						
							continue;
						}
					};
				
					// название поля
					/*if(strpos('@qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM_', xpath[p]) !== false) {
					
						// N'...' -> MS SQL UnicodeString
						if(xpath[p] == 'N' and isset($xpath[$p+1]) and $xpath[$p+1] == "'")
							continue;
					
						$start_p = $p;
						while(strpos('@qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM_0123456789-.', xpath[p]) !== false) 
							$p++;
						filter.get(expr)[$expr_key] = substr($xpath, $start_p, $p - $start_p); 
						filter.get(expr)[$expr_key.'_type'] = 'name';
						
						// возможно это function или unipath
						if(isset(xpath[p]) and xpath[p] == '(') {
							while(xpath[p] != ')')
								filter.get(expr)[$expr_key] .= $xpath[$p++];
							filter.get(expr)[$expr_key] .= $xpath[$p++];
							filter.get(expr)[$expr_key.'_type'] = 'function';
						}
				
						continue;
					}*/

					// число
					if("0123456789".indexOf(xpath[p]) > -1 || (xpath[p] == '-' 
					&& xpath.length > p+1 && "0123456789".indexOf(xpath[p+1]) > -1)) {
if(UniPath.debug_parse) System.out.println("number detected!");
						Integer len = 1;
						while(xpath.length > p+len && "0123456789-.".indexOf(xpath[p+len]) > -1) len++;
						String val = new String(xpath, p, len);
						String val_type = "number";
						p += len;
						
						// пропустим пробелы
						while(" \n\t".indexOf(xpath[p]) > -1) p++; 
						
						// возможно это список чисел
						ArrayList<String> val_array = null;
						if(xpath[p] == ',') {
							val_type = "list-of-number";
							val_array = new ArrayList<String>();
							val_array.add(val);
							
							// парсим список чисел
							while(xpath.length > p && "0123456789,\n\t ".indexOf(xpath[p]) > -1) {
								// пропустим пробелы (если есть)
								while("\n\t, ".indexOf(xpath[p]) > -1) p++; 
								
								// выделим число
								if("0123456789".indexOf(xpath[p]) > -1 
								|| (xpath[p] == '-' && xpath.length > p+1 && "0123456789".indexOf(xpath[p+1]) > -1)) {
									for(len = 1; xpath.length > p+len && "0123456789-.".indexOf(xpath[p+len]) > -1;) len++;
									val_array.add(new String(xpath, p, len));
									p += len;
									continue;
								}
								// дальше уже не число (закончился список чисел)
								else 
									break;
							}
if(UniPath.debug_parse) System.out.println("numbers = " + val_array.toString());
						}
						else {
if(UniPath.debug_parse) System.out.println("number = " + val);
						}
						
						if(expr_key.equals("left")) {
							filter.get(expr).left = val_array == null ? val : val_array;
							filter.get(expr).left_type = val_type;
						} 
						else {
							filter.get(expr).right = val_array == null ? val : val_array;
							filter.get(expr).right_type = val_type;
						}

						continue;
					}
					
					// строка
					if("'`\"".indexOf(xpath[p]) > -1 
					|| (xpath[p] == 'N' && xpath.length > p+1 && "'`\"".indexOf(xpath[p]) > -1)) {
if(UniPath.debug_parse) System.out.println("filter_string_start - "+p);
						String val;
						String val_type = "string";
						
						// подкоректируем начало MSSQL UnicodeString
						if(xpath[p] == 'N') { 
							p++; 
							val_type = "string-with-N"; 
						}
							
							
						Integer start_p = p++;

						// ```````...```````
						if(xpath.length > p+6 && xpath[p] == '`' && xpath[p+1] == '`' && xpath[p+2] == '`' && xpath[p+3] == '`' && xpath[p+4] == '`' && xpath[p+5] == '`' && xpath[p+6] == '`') {
							for(p += 6; xpath.length > p+6; p++) 
								if(xpath[p] == '`' && xpath[p+1] == '`' && xpath[p+2] == '`' && xpath[p+3] == '`' && xpath[p+4] == '`' && xpath[p+5] == '`' && xpath[p+6] == '`') break;
								else continue;
if(UniPath.debug_parse) System.out.println("filter_string_type = ```````, end = "+p);
							
							// не нашли конец строки?!
							if(xpath.length <= p+6)
								throw new RuntimeException("UniPath.__uni_parseUniPath(): Not found end ``````` in filter! ("+new String(xpath, start_p+7, xpath.length-start_p-7)+")");
								
							// пропускаем внутр. кавычки если есть
							while(xpath.length > p+7 && xpath[p+7] == '`') p++;
							
							// извлекаем содержимое строки
							val = new String(xpath, start_p+7, p-start_p-14);
							p += 7;
						}
						
						// ```...```
						else if(xpath.length > p+2 && xpath[p] == '`' && xpath[p+1] == '`' && xpath[p+2] == '`') {
							for(p += 3; xpath.length > p+3; p++) 
								if(xpath[p] == '`' && xpath[p+1] == '`' && xpath[p+2] == '`') break;
								else continue;
if(UniPath.debug_parse) System.out.println("filter_string_type = ```, end = "+p);

							// не нашли конец строки?!
							if(xpath.length <= p+6)
								throw new RuntimeException("UniPath.__uni_parseUniPath(): Not found end ``````` in filter! ("+new String(xpath, start_p+3, xpath.length)+")");
								
							// пропускаем внутр. кавычки если есть
							while(xpath.length > p+2 && xpath[p+2] == '`') p++;
							
							// извлекаем содержимое строки
							val = new String(xpath, start_p+3, p-start_p-6);
							p += 3;
						}
						
						// `...`, '...', "..."
						else {
							p += 1;
							while(xpath[p] != xpath[start_p]) { 
								if(xpath.length <= p+1)
									throw new RuntimeException("UniPath.__uni_parseUniPath(): Not found end of string "+xpath[start_p]);
								else
									p++;
							}
							val = new String(xpath, start_p+1, p-start_p-1);
							p += 1;
						}
						
if(UniPath.debug_parse) System.out.println("filter_string_end - "+val);
						
						// пропустим пробелы
						while(xpath.length > p+1 && " \n\t".indexOf(xpath[p]) > -1) p++; 
						
						// возможно это список строк
						ArrayList<String> val_array;
						if(xpath[p] == ',') {
							val_type = "list-of-" + val_type;
							val_array = new ArrayList<String>();
							val_array.add(val);
							p += 1;
							
							while(" \n\t".indexOf(xpath[p]) > -1) p++; 
							
							// парсим список строк
							while(xpath.length > p && "\"'`N\n\t ".indexOf(xpath[p]) > -1) {
							
								// пропустим пробелы
								while("\n\t, ".indexOf(xpath[p]) > -1) p++; 
								
								// MSSQL UnicodeString
								if(xpath[p] == 'N') p++;
								
								start_p = p++;

								// ```````...```````
								if(xpath.length > p+6 && xpath[p] == '`' && xpath[p+1] == '`' && xpath[p+2] == '`' && xpath[p+3] == '`' && xpath[p+4] == '`' && xpath[p+5] == '`' && xpath[p+6] == '`') {
									for(p += 6; xpath.length > p+6; p++) 
										if(xpath[p] == '`' && xpath[p+1] == '`' && xpath[p+2] == '`' && xpath[p+3] == '`' && xpath[p+4] == '`' && xpath[p+5] == '`' && xpath[p+6] == '`') break;
										else continue;
if(UniPath.debug_parse) System.out.println("filter_list_string_type = ```````, end = "+p);

									// не нашли конец строки?!
									if(xpath.length <= p+6)
										throw new RuntimeException("UniPath.__uni_parseUniPath(): Not found end ``````` in filter! ("+new String(xpath, start_p+7, xpath.length)+")");
									
									// пропускаем внутр. кавычки если есть
									while(xpath.length > p+7 && xpath[p+7] == '`') p++;
									
									// извлекаем содержимое строки
									val_array.add(new String(xpath, start_p+7, p-start_p-14));
									p += 7;
								}
								
								// ```...```
								if(xpath.length > p+2 && xpath[p] == '`' && xpath[p+1] == '`' && xpath[p+2] == '`') {
									for(p += 3; xpath.length > p+2; p++) 
										if(xpath[p] == '`' && xpath[p+1] == '`' && xpath[p+2] == '`') break;
										else continue;
if(UniPath.debug_parse) System.out.println("filter_list_string_type = ```, end = "+p);

									// не нашли конец строки?!
									if(xpath.length <= p+2)
										throw new RuntimeException("UniPath.__uni_parseUniPath(): Not found end ``` in filter! ("+new String(xpath, start_p+2, xpath.length)+")");
									
									// пропускаем внутр. кавычки если есть
									while(xpath.length > p+3 && xpath[p+3] == '`') p++;
									
									// извлекаем содержимое строки
									val_array.add(new String(xpath, start_p+3, p-start_p-6));
									p += 3;
								}
								
								// `...`, '...', "..."
								else {
if(UniPath.debug_parse) System.out.format("filter_list_string_type = %s, end = %i", xpath[start_p], p);
									while(xpath[p+1] != xpath[start_p]) p++;
									val_array.add(new String(xpath, start_p+1, p-start_p-2));
									p += 1;
								}
								
if(UniPath.debug_parse) System.out.println("filter_list_string = "+val_array.toString());
							}
						}
						
						if(expr_key.equals("left")) {
							filter.get(expr).left = val;
							filter.get(expr).left_type = val_type;
						} 
						else {
							filter.get(expr).right = val;
							filter.get(expr).right_type = val_type;
						}
						
						continue;
					}
					
					// текущий элемент
					if(xpath[p] == '.' && xpath.length > p+1 && xpath[p+1] != '/') {
						p++;
						if(expr_key.equals("left")) {
							filter.get(expr).left = ".";
							filter.get(expr).left_type = "dot";
						} 
						else {
							filter.get(expr).right = ".";
							filter.get(expr).right_type = "dot";
						}
						continue;
					}
					
					// вложенный unipath
					if("./@qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM_".indexOf(xpath[p])  > -1) {
						
						// N'...' -> MS SQL UnicodeString
						if(xpath[p] == 'N' && xpath.length > p+1 && xpath[p+1] == '\'')
							continue;
					
						Integer start_p = p;
if(UniPath.debug_parse) System.out.format("filter_inner_unipath_detected - %d%n", p);

						
						Integer func_flag = 0; 
						Integer unipath_flag = xpath[p] == '/' ? 1 : 0;
						Integer tmp_num = 0; 
						Integer[] tmp_stack = new Integer[]{ 99, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }; 
						while(xpath.length > p) {
						
							// ```````...
							if(tmp_stack[tmp_num] > 10 && xpath.length > p+6 && xpath[p] == '`' && xpath[p+1] == '`' && xpath[p+2] == '`' && xpath[p+3] == '`' && xpath[p+4] == '`' && xpath[p+5] == '`' && xpath[p+6] == '`') { 
								p += 6;
								tmp_stack[++tmp_num] = 5;
							}
							
							// ...```````
							else if(tmp_stack[tmp_num] == 5 && xpath.length > p+6 && xpath[p] == '`' && xpath[p+1] == '`' && xpath[p+2] == '`' && xpath[p+3] == '`' && xpath[p+4] == '`' && xpath[p+5] == '`' && xpath[p+6] == '`') {
								while(xpath.length > p+7 && xpath[p+7] == '`') p++;
								p += 6;
								tmp_num--;
							}
						
							// ...```
							else if(tmp_stack[tmp_num] == 4 && xpath.length > p+2 && xpath[p] == '`' && xpath[p+1] == '`' && xpath[p+2] == '`') {
								while(xpath.length > p+2 && xpath[p+2] == '`') p++;
								p += 2;
								tmp_num--;
							}
							
							// ```...
							else if(tmp_stack[tmp_num] > 10 && xpath.length > p+2 && xpath[p] == '`' && xpath[p+1] == '`' && xpath[p+2] == '`') { 
								p += 2;
								tmp_stack[++tmp_num] = 4;
							}
							
							// `...`
							else if(xpath[p] == '`' && tmp_stack[tmp_num] == 3) tmp_num--;
							else if(xpath[p] == '`' && tmp_stack[tmp_num] > 10) tmp_stack[++tmp_num] = 3;
						
							else if(xpath[p] == '[' && tmp_stack[tmp_num] > 10) { tmp_stack[++tmp_num] = unipath_flag = 11; }
							else if(xpath[p] == ']' && tmp_stack[tmp_num] == 11) tmp_num--;
							else if(xpath[p] == '(') { tmp_stack[++tmp_num] = func_flag = 12; }
							else if(xpath[p] == ')' && tmp_num == 0) break;
							else if(xpath[p] == ')' && tmp_stack[tmp_num] == 12) tmp_num--;
							else if(xpath[p] == '\'' && tmp_stack[tmp_num] == 1) tmp_num--;
							else if(xpath[p] == '\'' && tmp_stack[tmp_num] > 10) tmp_stack[++tmp_num] = 1;
							else if(xpath[p] == '"' && tmp_stack[tmp_num] == 2) tmp_num--;
							else if(xpath[p] == '"' && tmp_stack[tmp_num] > 10) tmp_stack[++tmp_num] = 2;
							else if(xpath[p] == '/' && tmp_stack[tmp_num] == 99) unipath_flag = 99;
							else if(" \n\t]=<>-+!".indexOf(xpath[p]) > -1 && tmp_num == 0) break;
if(UniPath.debug_parse) System.out.format("filter_inner_unipath_skip - %c %s tmp_stack[%d] = %d%n", xpath[p], new String(new char[tmp_num*3]).replace('\0', ' '), tmp_num, tmp_stack[tmp_num]);
							p++;
						}
						
						String val = new String(xpath, start_p, p-start_p);
						String val_type;
if(UniPath.debug_parse) System.out.format("filter_inner_unipath_result = %s, func_flag = %d, unipath_flag = %d%n", val, func_flag, unipath_flag);
						
						if(unipath_flag > 0)
							val_type = "unipath";
						else if(func_flag > 0)
							val_type = "function";
						else
							val_type = "name";
							
						if(expr_key.equals("left")) {
							filter.get(expr).left = val;
							filter.get(expr).left_type = val_type;
						} 
						else {
							filter.get(expr).right = val;
							filter.get(expr).right_type = val_type;
						}
						
						continue;
					}
					
if(UniPath.debug_parse) System.out.format("!!! unknown - %c (%04x)%n", xpath[p], (int) xpath[p]);
					p++; // непонятно что -> пропустим
				}
				p++; // ]
				
				// поддержка 2ух фильтров?
				/* if(isset($tree[count($tree)-1]['filter'.$suffix]))
					$tree[count($tree)-1]['filter2'.$suffix] = $filter;
				else
					$tree[count($tree)-1]['filter'.$suffix] = $filter; */
					
				continue;
			} // фильтрация
			
			// `'" строка
			if(xpath[p] == '`' || xpath[p] == '"' || xpath[p] == '\'') {
if(UniPath.debug_parse) System.out.format("string start - %d%n", p);
				int start_p = p++;
				String val;
				
// var_dump(xpath[p], substr($xpath, $p, 7));
				// ```````...```````
				if(xpath.length > p+6 && xpath[p] == '`' && xpath[p+1] == '`' && xpath[p+2] == '`' && xpath[p+3] == '`' && xpath[p+4] == '`' && xpath[p+5] == '`' && xpath[p+6] == '`') {
					for(p += 6; xpath.length > p+6; p++) 
						if(xpath[p] == '`' && xpath[p+1] == '`' && xpath[p+2] == '`' && xpath[p+3] == '`' && xpath[p+4] == '`' && xpath[p+5] == '`' && xpath[p+6] == '`') break;
						else continue;
if(UniPath.debug_parse) System.out.format("filter_string_type = ```````, end = %s%n", p);
					
					// не нашли конец строки?!
					if(xpath.length <= p+6)
						throw new RuntimeException("UniPath.__uni_parseUniPath(): Not found end ``````` in filter! ("+new String(xpath, start_p+7, xpath.length)+")");
						
					// пропускаем внутр. кавычки если есть
					while(xpath.length > p+7 && xpath[p+7] == '`') p++;
					
					// извлекаем содержимое строки
					val = new String(xpath, start_p+7, p-start_p-14);
					p += 7;
				}
				
				// ```...```
				else if(xpath.length > p+2 && xpath[p] == '`' && xpath[p+1] == '`' && xpath[p+2] == '`') {
					for(p += 3; xpath.length > p+3; p++) 
						if(xpath[p] == '`' && xpath[p+1] == '`' && xpath[p+2] == '`') break;
						else continue;
if(UniPath.debug_parse) System.out.format("filter_string_type = ```, end = %d%n", p);

					// не нашли конец строки?!
					if(xpath.length <= p+6)
						throw new RuntimeException("UniPath.__uni_parseUniPath(): Not found end ``````` in filter! ("+new String(xpath, start_p+3, xpath.length)+")");
						
					// пропускаем внутр. кавычки если есть
					while(xpath.length > p+2 && xpath[p+2] == '`') p++;
					
					// извлекаем содержимое строки
					val = new String(xpath, start_p+3, p-start_p-6);
					p += 3;
				}
				
				// `...`, '...', "..."
				else {
					p += 1;
					while(xpath[p] != xpath[start_p]) { 
						if(xpath.length <= p+1)
							throw new RuntimeException("UniPath.__uni_parseUniPath(): Not found end of string "+xpath[start_p]);
						else
							p++;
					}
					val = new String(xpath, start_p+1, p-start_p-1);
					p += 1;
				}

				tree.get(tree.size()-1).name = val;
				
if(UniPath.debug_parse) System.out.println(val);
				continue;
			}
			
			p++; // попробуем пропустить непонятные символы
			
		}; // while(xpath)
		
		// пропишем путь у последнего уровня пути
		if(tree.size() > 0)
			tree.get(tree.size()-1).unipath = new String(xpath);
			
		return tree;
	
	}
	
	// вытаскивает список аргументов внутри функции
	static public String __parseFuncArgs(char[] string, HashMap<String, Object> result, HashMap<String, String> result_types) {

		String func_name = null;
		String arg_key = "0";
		String last_string = null;
		String in_string = null;
		Integer in_binary_string = null; // внутри строки?
		Integer brakets_level = 0; // уровень вложенности скобок
		Integer arg_start = 0;
		String mode = ""; int p = 0; int len = 0;
		for(int prt_cnt = 0; prt_cnt < UniPath.__prt_cnt; prt_cnt++) {
		
			if(in_binary_string != null)
				for(len = 0; string.length > p+len && string[p+len] != '`';) len++;
			else if(in_string != null)
				for(len = 0; string.length > p+len && in_string.indexOf(string[p+len]) == -1;) len++;
			else
				for(len = 0; string.length > p+len && ",()[]'\"`=".indexOf(string[p+len]) == -1;) len++;
			
			if(UniPath.debug_parse) System.out.format("*** %s [%s]%n |%s|%s -- p:len=%d:%d brakets_level=%d, arg_start=%s%n", mode, 
				arg_key,
				new String(string, p, len), 
				new String(string, p+len, string.length-p-len), 
				p, len, brakets_level, arg_start);

			if(len+p == string.length) break; // дошли до конца строки
			
			// выделим имя функции
			if(brakets_level == 0 && string[p+len] == '(' && in_string == null && in_binary_string == null) {
				func_name = new String(string, 0, len).trim();
				brakets_level++;
				arg_start = p = len+1;
				mode = "func_name";
				continue;
			}
			
			// (,[
			if(string.length > p+len && (string[p+len] == '(' || string[p+len] == '[') && in_string == null && in_binary_string == null) {
				brakets_level++;
				p += len+1;
				mode = "open_braket";
				continue;
			}
			
			// ),[
			if(string.length > p+len && (string[p+len] == ')' || string[p+len] == ']') && in_string == null && in_binary_string == null) {
				brakets_level--;
				
				if(brakets_level == 0) {
					if(last_string != null) {
						result.put(arg_key, last_string);
						result_types.put(arg_key, mode == "in_string_end_N" ? "string-with-N" : "string");
						last_string = null;
					} 
					else if(arg_start < p+len) {
						if(arg_start+4 == p+len && string[arg_start] == 'n' && string[arg_start+1] == 'u' && string[arg_start+2] == 'l' && string[arg_start+3] == 'l') {
							result.put(arg_key, null);
							result_types.put(arg_key, "null");
						} 
						else {
							result.put(arg_key, new String(string, arg_start, p+len-arg_start).trim());
							result_types.put(arg_key, "number");
							
							// может это не число, а unipath?
							for(int i = arg_start; i < p+len; i++) {
								if("0123456789. \t\n\r".indexOf(string[i]) > -1) continue;
								else {
									result_types.put(arg_key, "unipath");
									break;
								}
							}
						}
					}
					
					p = arg_start = p + len + 1;
					mode = "close_braket_end";
				} 
				else {
					p += len+1;
					mode = "close_braket";
				}
				
				last_string = null; // на всяк случай
				continue;
			}
			
			// ' - string
			if((string[p+len] == '\'' 
			|| (string[p+len] == 'N' && string.length >= p+len+1 && string[p+len+1] == 'N'))
			&& in_string == null && in_binary_string == null) {
				if(last_string != null)
					throw new RuntimeException("UniPath: __uni_parseFuncArgs(): unexpected single quote ' on position "+(p+len)+" - "+new String(string, p, string.length-p));
					
				in_string = "'";
				last_string = "";
				mode = string[p+len-1] == 'N' ? "in_string_start_N" : "in_string_start";
				p += len + 1;
				continue;
			}
			
			// '' and string end
			if(string[p+len] == '\'' && in_string == "'" && in_binary_string == null) {
				/*if(substr_compare($string, "''", $p+$len, 2) == 0) {
					$last_string .= substr($string, $p, $len+1);
					$p += $len+1;
					$mode = 'in_string_escp1';
					continue;
				} elseif($p+$len > 0 and $string[$p+$len-1] == "'" and $last_string != '') {
					$p += $len+1;
					$mode = 'in_string_escp2';
					continue;
				} else {*/
					last_string = last_string + new String(string, p, len);
					in_string = null;
					p += len+1;
					mode = mode == "in_string_start_N" ? "in_string_end_N" : "in_string_end";
					continue;
				//}
			}
			
			// " - string
			if(string[p+len] == '"' && in_string == null && in_binary_string == null) {
				in_string = "\"";
				last_string = "";
				p += len+1;
				mode = "in_string_start";
				continue;
			}
			
			// "" and string end
			if(string[p+len] == '"' && in_string == "\"" && in_binary_string == null) {
				if(string.length > p+len+1 && string[p+len+1] == '"') {
					last_string = last_string + new String(string, p, len+1);
					p += len+1;
					mode = "in_string_escp1";
					continue;
				} 
				else if(p+len > 0 && string[p+len-1] == '"' && last_string.equals("")) {
					p += len+1;
					mode = "in_string_escp2";
					continue;
				} 
				else {
					last_string = last_string + new String(string, p, len);
					in_string = null;
					p += len+1;
					mode = "in_string_end";
					continue;
				}
			}
			
			// ``` - binary-string
			if(string[p+len] == '`' && in_string == null && in_binary_string == null) {
				if(string.length >= p+len+6 && string[p+len] == '`' && string[p+len+1] == '`' && string[p+len+2] == '`' && string[p+len+3] == '`' && string[p+len+4] == '`' && string[p+len+5] == '`' && string[p+len+6] == '`')
					in_binary_string = 7;
				else if(string.length >= p+len+2 && string[p+len] == '`' && string[p+len+1] == '`' && string[p+len+2] == '`')
					in_binary_string = 3;
				else
					in_binary_string = 1;
				
				if(UniPath.debug_parse) System.out.format("  in_binary_string = %s%n", in_binary_string); 
					
				// если был / до строки, то мы внутри unipath!
				Integer slash_pos = null;
				for(int i = arg_start; i < string.length; i++)
					if(string[i] == '/') { slash_pos = i; break; }
				last_string = (slash_pos != null && slash_pos < p+len) ? null : "";
				
				p += len+in_binary_string;
				mode = "in_binary_string_start";
				continue;
			}
			
			// ``` - binary-string end
			if(string[p+len] == '`' && in_string == null && in_binary_string != null) {
				Integer len2 = 0;
				for(int i = p+len; i < string.length && string[i] == '`'; i++) len2++;
				
				// если апострафов больше или столько же сколько открывающих, то это конец строки
				if(len2 >= in_binary_string) {
					if(last_string == null)
						last_string = null; // строка была в unipath
					else
						last_string = last_string + new String(string, p, len + (len2-in_binary_string));
					in_binary_string = null;
					p += len+len2;
					mode = "in_binary_string_end";
				} 
				
				// в середине встретились апострофы
				else {
					last_string = last_string + new String(string, p, len+len2);
					p += len+len2;
				}
				
				if(UniPath.debug_parse) System.out.format("  binary_string_end: %s%n", last_string);
				
				continue;
			}

			// , - skip
			if(string[p+len] == ',' && in_string == null && in_binary_string == null && brakets_level > 1) {
				p = p + len + 1;
				last_string = null;
				mode = "inner_commar_skip";
				continue;
			}
			
			// , - argument
			if(string[p+len] == ',' && in_string == null && in_binary_string == null && brakets_level == 1) {
					
				if(last_string != null) {
					result.put(arg_key, last_string);
					result_types.put(arg_key, mode.equals("in_string_end_N") ? "string-with-N" : "string");
					last_string = null;
				} 
				else {
					if(arg_start+4 == p+len && string[arg_start] == 'n' && string[arg_start+1] == 'u' && string[arg_start+2] == 'l' && string[arg_start+3] == 'l') {
						result.put(arg_key, null);
						result_types.put(arg_key, "null");
					} 
					else {
						result.put(arg_key, new String(string, arg_start, p+len-arg_start).trim());
						result_types.put(arg_key, "number");
						
						// может это не число, а unipath?
						for(int i = arg_start; i < p+len; i++) {
							if("0123456789. \t\n\r".indexOf(string[i]) > -1) continue;
							else {
								result_types.put(arg_key, "unipath");
								break;
							}
						}
					}
				}
				
				if(UniPath.debug_parse) System.out.format("  key_put: %s=%s%n", arg_key, result.get(arg_key).toString());
				
				p = arg_start = p + len + 1;
				mode = arg_key;
				
				// сгенерируем следующий arg_key
				for(Integer arg_num = 0; arg_num < UniPath.__prt_cnt; arg_num++)
					if(result.containsKey(arg_num.toString()) == false) { 
						arg_key = arg_num.toString();
						break;
					}
				
				continue;
			}

			// = - named argument
			if(string[p+len] == '=' && in_string == null && in_binary_string == null && brakets_level >= 1) {

				// возможно мы внутри фильтра в unipath? или внутри других скобок
				Integer filter_open_pos = null;
				for(int i = arg_start; i < string.length; i++)
					if(string[arg_start] == '[') { filter_open_pos = i; break; }
				if(brakets_level > 1 || filter_open_pos != null && filter_open_pos < p+len) {
					p += len+1;
					mode = "skip_named_arg";
					continue;
				}
			
				if(last_string != null) {
					arg_key = last_string;
					last_string = null;
				} 
				else {
					arg_key = new String(string, arg_start, len).trim();
				}
				arg_start = p+len+1;
				p += len+1;
				mode = "named_arg";
				continue;
			}
		}
		
		return func_name;
	}

	/* static public void fs(ArrayList<Node> tree, Integer lv) {
		tree.get(lv).data = "file://";
		tree.get(lv).metadata.clear();
		tree.get(lv).metadata.put("0", "string/local-filesystem");
		tree.get(lv).metadata.put("key()", "file://");
	} */
	
	static public void toJson(ArrayList<Node> tree, Integer lv) {
		StringBuffer out = new StringBuffer();
		toJson(tree.get(lv-1).data, out, 0, 10, true);

		tree.get(lv).data = out.toString();
		tree.get(lv).metadata.put("0", "String/JSON");
	}

	static public void toJson(Object value, StringBuffer out, Integer level, Integer max_level, Boolean indent) {

		if(value == null) {
			out.append("null");
			return;
		}
		
		if(level > max_level){
			escapeForJson(value.toString(), out);
			return;
		}
		
		if(value instanceof String){
			out.append('\"');
			escapeForJson((String)value, out);
			out.append('\"');
			return;
		}
		
		if(value instanceof Double){
			if(((Double)value).isInfinite() || ((Double)value).isNaN())
				out.append("null");
			else
				out.append(value.toString());
			return;
		}
		
		if(value instanceof Float){
			if(((Float)value).isInfinite() || ((Float)value).isNaN())
				out.append("null");
			else
				out.append(value.toString());
			return;
		}		
		
		if(value instanceof Number || value instanceof Boolean){
			out.append(value.toString());
			return;
		}
		
		if(value instanceof Map){
			Map map = (Map) value;
			boolean first = true;
			Iterator iter=map.entrySet().iterator();
		
			out.append('{');
			while(iter.hasNext()){
				if(first) first = false;
				else out.append(',');
				
				Map.Entry entry = (Map.Entry)iter.next();
				out.append('\"');
				escapeForJson(String.valueOf(entry.getKey()), out);
				out.append('\"');
				out.append(':');
				toJson(entry.getValue(), out, level+1, max_level, indent);
			}
			out.append('}');
			
			return;
		}
		
		if(value instanceof Collection){
			boolean first = true;
			Iterator iter = ((Collection) value).iterator();
		
			out.append('[');
			while(iter.hasNext()){
				if(first) first = false;
				else out.append(',');
				
				Object value2 = iter.next();
				if(value2 == null){
					out.append("null");
					continue;
				}
			
				toJson(value2, out, level+1, max_level, indent);
			}
			out.append(']');
			
			return;
		}
		
		if(value instanceof int[]) {
			out.append("[");
			for(int i = 0; i < ((int[]) value).length; i++) {
				if(i > 0) out.append(",");
				toJson(((int[]) value)[i], out, level+1, max_level, indent);
			}
			out.append("]");
			return;
		}
		
		if(value instanceof char[]) {
			out.append("[\"");
			for(int i = 0; i < ((char[]) value).length; i++) {
				if(i > 0) out.append("\",\"");
				escapeForJson(String.valueOf(((char[]) value)[i]), out);
			}
			out.append("\"]");
			return;
		}
		
		if(value instanceof Object[]) {
			Object[] array = (Object[]) value;
			
			out.append("[");
			toJson(array[0], out, level+1, max_level, indent);
			for(int i = 1; i < array.length; i++){
				out.append(",");
				toJson(array[i], out, level+1, max_level, indent);
			}
			out.append("]");
			
			return;
		}
		
		if(value instanceof Object) {
			out.append("{");

			try {
				Class<?> thisClass = Class.forName(value.getClass().getName());
				while(thisClass != null) {
					Field[] aClassFields = thisClass.getDeclaredFields();
					Boolean is_first = true;
					for(Field f : aClassFields) {
						if(is_first) is_first = false;
						else out.append(", ");
						
						if(indent == true) {
							out.append("\n");
							out.append(new String(new char[level*2]).replace('\0', ' '));
						}
						
						out.append("\"");
						escapeForJson(f.getName(), out);
						out.append("\": ");
						
						f.setAccessible(true);
						try {
							Object obj = f.get(value);
							toJson(obj, out, level+1, max_level, indent);
						} catch(IllegalAccessException ex) {
							out.append("\"*IllegalAccessException*\"");
						}
					}
					
					if(thisClass.getSuperclass() == null) break;
					
					thisClass = Class.forName(thisClass.getSuperclass().getName());
				}
			}
			catch(ClassNotFoundException ex) {
				ex.printStackTrace();
			}
			
			if(indent == true) {
				out.append("\n");
				out.append(new String(new char[(level-1)*2]).replace('\0', ' '));
			}
			
			out.append('}');
			return;
		}
		
		out.append('"');
		escapeForJson(value.toString(), out);
		out.append('"');
	}
	
	static public void escapeForJson(String s, StringBuffer sb) {
    	final int len = s.length();
		for(int i=0;i<len;i++){
			char ch=s.charAt(i);
			switch(ch){
			case '"': sb.append("\\\""); break;
			case '\\': sb.append("\\\\"); break;
			case '\b': sb.append("\\b"); break;
			case '\f': sb.append("\\f"); break;
			case '\n': sb.append("\\n"); break;
			case '\r': sb.append("\\r"); break;
			case '\t': sb.append("\\t"); break;
			case '/': sb.append("\\/"); break;
			default:
                //Reference: http://www.unicode.org/versions/Unicode5.1.0/
				if((ch>='\u0000' && ch<='\u001F') || (ch>='\u007F' && ch<='\u009F') || (ch>='\u2000' && ch<='\u20FF')){
					String ss=Integer.toHexString(ch);
					sb.append("\\u");
					for(int k=0;k<4-ss.length();k++){
						sb.append('0');
					}
					sb.append(ss.toUpperCase());
				}
				else{
					sb.append(ch);
				}
			}
		}//for
	}
}