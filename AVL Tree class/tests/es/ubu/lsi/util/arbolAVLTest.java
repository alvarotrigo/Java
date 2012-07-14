package es.ubu.lsi.util;
import java.util.*;
import java.lang.String;
import org.junit.*;
import static org.junit.Assert.*;

/**
 * Test que verifica el funcionamiento de la clase ArbolAVL. Contiene un conjunto de 
 * tests que ponen a prueba la funcionalidad de los metodos de la clase.   
 * 
 * @author Alvaro Trigo Lopez.
 *
 * @version 5.00 2009/6/14
 */
public class arbolAVLTest {
	
	/**
	 * objetos arbol.
	 */
	private ArbolAVL<Integer> arbol, arbol2, arbol3, arbol4, arbol5;
	
	public arbolAVLTest(){
		arbol = new ArbolAVL<Integer>();
	}
	
	/**
	 * Comprueba el correcto funcionamiento del método add en una estructura de arbol.
	 * Para ello va probando las diferentes situaciones en las que se puede llamar a la funcion esperando el resultado correcto.
	 */
	@Test
	public void addTest(){
		//comprobamos que devuelve true cuando el arbol se crea
		assertTrue("add no funciona bien", arbol.add(4));
		
		//comprobamos que devuelve true cuando el arbol se modifica
		assertTrue("add no funciona bien", arbol.add(5));
		
		//comprobamos que devuelve false cuando no se modifica
		assertFalse("add no funciona bien", arbol.add(4));
				
		//comprobamos que la raiz es correcta cuando no requiere de rotaciones
		assertSame("add no funciona bien", arbol.preOrden().get(0), 4);
		arbol.add(3);
		assertSame("add no funciona bien", arbol.preOrden().get(0), 4);
		arbol.add(1);
		assertSame("add no funciona bien", arbol.preOrden().get(0), 4);
		arbol.add(2);
		assertSame("add no funciona bien", arbol.preOrden().get(0), 4);
		arbol.add(6);
		assertSame("add no funciona bien", arbol.preOrden().get(0), 4);		
	}
	
	/**
	 * Comprueba el correcto funcionamiento del método estructurar en una estructura de arbol.
	 * Para ello va probando las diferentes situaciones en las que se puede llamar a la funcion esperando el resultado correcto.
	 */
	@Test
	public void estructurarTest(){
		//comprobamos rotacion simple a la derecha cuando cambia la raiz
		arbol2 = new ArbolAVL<Integer>();
		arbol2.add(4);
		arbol2.add(3);
		assertSame("add no funciona bien", arbol2.preOrden().get(0), 4);
		arbol2.add(1);
		assertSame("add no funciona bien", arbol2.preOrden().get(0), 3);
		
		//comprobamos rotacion simple a la izquierda cuando cambia la raiz
		arbol3 = new ArbolAVL<Integer>();
		arbol3.add(4);
		arbol3.add(3);
		assertSame("add no funciona bien", arbol3.preOrden().get(0), 4);
		arbol3.add(1);
		assertSame("add no funciona bien", arbol3.preOrden().get(0), 3);
		
		//comprobamos rotacion compuesta a la derecha
		arbol4 = new ArbolAVL<Integer>();
		arbol4.add(5);
		arbol4.add(3);
		arbol4.add(10);
		arbol4.add(11);
		arbol4.add(9);
		assertSame("add no funciona bien", arbol4.preOrden().get(0), 5);
		arbol4.add(8);
		assertSame("add no funciona bien", arbol4.preOrden().get(0), 9);
		
		//comprobamos rotacion compuesta a la izquierda
		arbol5 = new ArbolAVL<Integer>();
		arbol5.add(5);
		arbol5.add(3);
		arbol5.add(10);
		arbol5.add(11);
		arbol5.add(9);
		assertSame("add no funciona bien", arbol5.preOrden().get(0), 5);
		arbol5.add(8);
		assertSame("add no funciona bien", arbol5.preOrden().get(0), 9);
	}
	
	/**
	 * Comprueba el correcto funcionamiento del método extraeFactorE en una estructura de arbol.
	 * Para ello va probando las diferentes situaciones en las que se puede llamar a la funcion esperando el resultado correcto.
	 */
	@Test
	public void extraeFactorETest(){
		//el factor de equilibrio de un nodo hoja / raiz es 0
		arbol.add(5);
		assertSame("extraerFactorETest no funciona bien", arbol.extraeFactorE(arbol.getNodo(5)), 0);
		arbol.add(6);
		arbol.add(3);
		assertSame("extraerFactorETest no funciona bien", arbol.extraeFactorE(arbol.getNodo(6)), 0);
		assertSame("extraerFactorETest no funciona bien", arbol.extraeFactorE(arbol.getNodo(3)), 0);
		
		//el factor de equilibrio de un nodo con 2 hijos hoja, es 0
		assertSame("extraerFactorETest no funciona bien", arbol.extraeFactorE(arbol.getNodo(5)), 0);
		
		//comprobamos que el FE es -1 cuando debe serlo
		arbol.add(2);
		assertSame("extraerFactorETest no funciona bien", arbol.extraeFactorE(arbol.getNodo(5)), -1);
		arbol.add(4);
		assertSame("extraerFactorETest no funciona bien", arbol.extraeFactorE(arbol.getNodo(5)), -1);
		arbol.remove(2);
		assertSame("extraerFactorETest no funciona bien", arbol.extraeFactorE(arbol.getNodo(5)), -1);
		
		//comprobamos que el FE es +1 cuando debe serlo
		arbol.remove(4);
		arbol.remove(2);
		arbol.remove(3);
		arbol.remove(6);
		arbol.add(7);
		assertSame("extraerFactorETest no funciona bien", arbol.extraeFactorE(arbol.getNodo(5)), 1);
		arbol.add(3);
		arbol.add(8);
		assertSame("extraerFactorETest no funciona bien", arbol.extraeFactorE(arbol.getNodo(5)), 1);
		arbol.add(6);
		assertSame("extraerFactorETest no funciona bien", arbol.extraeFactorE(arbol.getNodo(5)), 1);
		arbol.remove(8);
		assertSame("extraerFactorETest no funciona bien", arbol.extraeFactorE(arbol.getNodo(5)), 1);
		
		//factor de equilibrio tras reestructurar el arbol por todos los modos posibles
		//comprobamos rotacion simple a la derecha cuando cambia la raiz
		arbol2 = new ArbolAVL<Integer>();
		arbol2.add(4);
		arbol2.add(3);
		arbol2.add(1);
		assertSame("extraerFactorETest no funciona bien", arbol.extraeFactorE(arbol2.getNodo(1)), 0);
		assertSame("extraerFactorETest no funciona bien", arbol.extraeFactorE(arbol2.getNodo(4)), 0);
		assertSame("extraerFactorETest no funciona bien", arbol.extraeFactorE(arbol2.getNodo(3)), 0);
		
		
		//comprobamos rotacion simple a la izquierda cuando cambia la raiz
		arbol3 = new ArbolAVL<Integer>();
		arbol3.add(1);
		arbol3.add(3);
		arbol3.add(4);
		assertSame("extraerFactorETest no funciona bien", arbol.extraeFactorE(arbol3.getNodo(1)), 0);
		assertSame("extraerFactorETest no funciona bien", arbol.extraeFactorE(arbol3.getNodo(4)), 0);
		assertSame("extraerFactorETest no funciona bien", arbol.extraeFactorE(arbol3.getNodo(3)), 0);
		
		//comprobamos rotacion compuesta a la derecha
		arbol4 = new ArbolAVL<Integer>();
		arbol4.add(5);
		arbol4.add(3);
		arbol4.add(10);
		arbol4.add(11);
		arbol4.add(9);
		arbol4.add(8);
		assertSame("extraerFactorETest no funciona bien", arbol.extraeFactorE(arbol4.getNodo(3)), 0);
		assertSame("extraerFactorETest no funciona bien", arbol.extraeFactorE(arbol4.getNodo(8)), 0);
		assertSame("extraerFactorETest no funciona bien", arbol.extraeFactorE(arbol4.getNodo(11)), 0);
		assertSame("extraerFactorETest no funciona bien", arbol.extraeFactorE(arbol4.getNodo(5)), 0);
		assertSame("extraerFactorETest no funciona bien", arbol.extraeFactorE(arbol4.getNodo(10)), 1);
		assertSame("extraerFactorETest no funciona bien", arbol.extraeFactorE(arbol4.getNodo(9)), 0);
		
		//comprobamos rotacion compuesta a la izquierda
		arbol5 = new ArbolAVL<Integer>();
		arbol5.add(10);
		arbol5.add(7);
		arbol5.add(12);
		arbol5.add(6);
		arbol5.add(8);
		arbol5.add(9);	
		assertSame("extraerFactorETest no funciona bien", arbol.extraeFactorE(arbol5.getNodo(6)), 0);
		assertSame("extraerFactorETest no funciona bien", arbol.extraeFactorE(arbol5.getNodo(9)), 0);
		assertSame("extraerFactorETest no funciona bien", arbol.extraeFactorE(arbol5.getNodo(12)), 0);
		assertSame("extraerFactorETest no funciona bien", arbol.extraeFactorE(arbol5.getNodo(10)), 0);
		assertSame("extraerFactorETest no funciona bien", arbol.extraeFactorE(arbol5.getNodo(7)), -1);
		assertSame("extraerFactorETest no funciona bien", arbol.extraeFactorE(arbol5.getNodo(8)), 0);
		
	}
	
	/**
	 * Comprueba el correcto funcionamiento del método rotacionSimpleIzquierda en una estructura de arbol.
	 * Para ello va probando las diferentes situaciones en las que se puede llamar a la funcion esperando el resultado correcto.
	 */
	@Test
	public void rotacionSimpleIzquierdaTest(){

		//comprobamos que nos devuelve la raiz una vez estructurado		
		arbol.add(2);
		arbol.add(3);
		arbol.add(4);
		
		assertSame("rotacionSimpleIzquierda no fuciona bien", arbol.extraeDato(arbol.getRaiz()), 3);
	}
	
	/**
	 * Comprueba el correcto funcionamiento del método rotacionSimpleDerecha en una estructura de arbol.
	 * Para ello va probando las diferentes situaciones en las que se puede llamar a la funcion esperando el resultado correcto.
	 */
	@Test
	public void rotacionSimpleDerechaTest(){

		//comprobamos que nos devuelve la raiz una vez estructurado	
		arbol.add(4);
		arbol.add(3);
		arbol.add(2);
		
		assertSame("rotacionSimpleDerecha no fuciona bien", arbol.extraeDato(arbol.getRaiz()), 3);
	}
	
	/**
	 * Comprueba el correcto funcionamiento del método rotacionCompuestaIzquierda en una estructura de arbol.
	 * Para ello va probando las diferentes situaciones en las que se puede llamar a la funcion esperando el resultado correcto.
	 */
	@Test
	public void rotacionCompuestaIzquierdaTest(){

		//comprobamos que nos devuelve la raiz una vez estructurado
				
		arbol.add(10);
		arbol.add(7);
		arbol.add(12);
		arbol.add(6);
		arbol.add(8);
		arbol.add(9);
		
		assertSame("rotacionCompuestaIzquierda no fuciona bien",arbol.extraeDato(arbol.getRaiz()), 8);
		
		arbol2 = new ArbolAVL<Integer>();
		arbol2.add(40);
		arbol2.add(20);
		arbol2.add(25);
		arbol2.add(15);
		arbol2.add(10);
		arbol2.add(43);
		arbol2.add(32);
		arbol2.add(41);
		arbol2.add(45);
		arbol2.add(42);
		
		assertSame("preOrden no funciona bien", arbol2.preOrden().get(0), 25);
		assertSame("preOrden no funciona bien", arbol2.preOrden().get(1), 15);
		assertSame("preOrden no funciona bien", arbol2.preOrden().get(2), 10);
		assertSame("preOrden no funciona bien", arbol2.preOrden().get(3), 20);
		assertSame("preOrden no funciona bien", arbol2.preOrden().get(4), 41);
		assertSame("preOrden no funciona bien", arbol2.preOrden().get(5), 40);
		assertSame("preOrden no funciona bien", arbol2.preOrden().get(6), 32);
		assertSame("preOrden no funciona bien", arbol2.preOrden().get(7), 43);
		assertSame("preOrden no funciona bien", arbol2.preOrden().get(8), 42);
		assertSame("preOrden no funciona bien", arbol2.preOrden().get(9), 45);
		
	}

	/**
	 * Comprueba el correcto funcionamiento del método rotacionCompuestaDerecha en una estructura de arbol.
	 * Para ello va probando las diferentes situaciones en las que se puede llamar a la funcion esperando el resultado correcto.
	 */
	@Test
	public void rotacionCompuestaDerechaTest(){

		//comprobamos que nos devuelve la raiz una vez estructurado
		arbol.add(5);
		arbol.add(3);
		arbol.add(10);
		arbol.add(9);
		arbol.add(11);
		arbol.add(8);
		
		assertSame("rotacionCompuestaDerecha no fuciona bien", arbol.extraeDato(arbol.getRaiz()), 9);
		
		arbol2 = new ArbolAVL<Integer>();
		arbol2.add(40);
		arbol2.add(20);
		arbol2.add(25);
		arbol2.add(15);
		arbol2.add(10);
		arbol2.add(43);
		arbol2.add(32);
		arbol2.add(9);
		arbol2.add(13);
		arbol2.add(14);
		
		assertSame("preOrden no funciona bien", arbol2.preOrden().get(0), 25);
		assertSame("preOrden no funciona bien", arbol2.preOrden().get(1), 13);
		assertSame("preOrden no funciona bien", arbol2.preOrden().get(2), 10);
		assertSame("preOrden no funciona bien", arbol2.preOrden().get(3), 9);
		assertSame("preOrden no funciona bien", arbol2.preOrden().get(4), 15);
		assertSame("preOrden no funciona bien", arbol2.preOrden().get(5), 14);
		assertSame("preOrden no funciona bien", arbol2.preOrden().get(6), 20);
		assertSame("preOrden no funciona bien", arbol2.preOrden().get(7), 40);
		assertSame("preOrden no funciona bien", arbol2.preOrden().get(8), 32);
		assertSame("preOrden no funciona bien", arbol2.preOrden().get(9), 43);
	}
	
	/**
	 * Comprueba el correcto funcionamiento del método equilibrado en una estructura de arbol.
	 * Para ello va probando las diferentes situaciones en las que se puede llamar a la funcion esperando el resultado correcto.
	 */
	@Test
	public void equilibradoTest(){
		
		//el arbol vacio estará equilibrado
		assertSame("equilibrado no funciona bien", arbol.equilibrado(null), 0);
		
		//el arbol con un solo nodo raiz tambien
		arbol.add(10);
		assertSame("equilibrado no funciona bien", arbol.equilibrado(arbol.getNodo(10)), 1);
		
		//los arboles con FE <2 también
		arbol.add(7);
		assertSame("equilibrado no funciona bien", arbol.equilibrado(arbol.getNodo(10)), 2);
		arbol.add(12);
		assertSame("equilibrado no funciona bien", arbol.equilibrado(arbol.getNodo(10)), 2);
		arbol.add(6);
		arbol.add(8);
		assertSame("equilibrado no funciona bien", arbol.equilibrado(arbol.getNodo(10)), 3);
		
		//comprobamos que deja de estar equilibrado
	//	Nodo<Integer> nodo9 = new Nodo<Integer>(9);
	//	arbol.getNodo(8).setDerecha(nodo9);
	//	assertSame("equilibrado no funciona bien", arbol.equilibrado(arbol.getNodo(10)), -1);
	}

	/**
	 * Comprueba el correcto funcionamiento del método padre en una estructura de arbol.
	 * Para ello va probando las diferentes situaciones en las que se puede llamar a la funcion esperando el resultado correcto.
	 */
	@Test	
	public void padreTest(){
		//el padre de un nodo hoja es él mismo
		arbol.add(5);
		assertEquals("padre no funciona bien", arbol.padre(arbol.getNodo(5)),arbol.getNodo(5));
		
		//comprobamos que el padre sea el correcto
		arbol.add(6);
		assertEquals("padre no funciona bien", arbol.padre(arbol.getNodo(6)),arbol.getNodo(5));
		
		arbol.add(4);
		assertEquals("padre no funciona bien", arbol.padre(arbol.getNodo(4)),arbol.getNodo(5));
		
		arbol.add(7);
		assertEquals("padre no funciona bien", arbol.padre(arbol.getNodo(7)),arbol.getNodo(6));
	}
	
	/**
	 * Comprueba el correcto funcionamiento del método addAll en una estructura de arbol.
	 * Para ello va probando las diferentes situaciones en las que se puede llamar a la funcion esperando el resultado correcto.
	 */
	@Test
	public void addAllTest(){
		//cuando el arbol está vacio mete todos
		Collection<Integer> col = new ArrayList<Integer>();
		Collection<Integer> col2 = new ArrayList<Integer>();
		Collection<Integer> col3 = new ArrayList<Integer>();
		col.add(5);
		col.add(3);
		col.add(10);
		col.add(9);
		col.add(11);
		col.add(8);
		
		assertTrue("addAll no funciona bien", arbol.addAll(col));

		//cuando metemos los mismos de nuevo no añade nada
		assertFalse("addAll no funciona bien", arbol.addAll(col));
		
		//comprobamos que metiendo uno existente no cambia el arbol
		col2.add(3);
		assertFalse("addAll no funciona bien", arbol.addAll(col2));
		
		//comprobamos que metiendo uno inexistente cambia el arbol
		col3.add(1);
		assertTrue("addAll no funciona bien", arbol.addAll(col3));
	}
	
	/**
	 * Comprueba el correcto funcionamiento del método clear en una estructura de arbol.
	 * Para ello va probando las diferentes situaciones en las que se puede llamar a la funcion esperando el resultado correcto.
	 */
	@Test
	public void clearTest(){
		
		//borra un arbol vacio
		assertSame("clear no funciona bien", arbol.size(), 0);
		arbol.clear();
		assertSame("clear no funciona bien", arbol.size(), 0);
		
		//borra un arbol con un elemento
		arbol.add(5);
		assertSame("clear no funciona bien", arbol.size(), 1);
		arbol.clear();
		assertSame("clear no funciona bien", arbol.size(), 0);
		
		//boora un arbol con varios elemementos	
		arbol.add(3);
		arbol.add(5);
		arbol.add(2);
		assertSame("clear no funciona bien", arbol.size(), 3);
		arbol.clear();
		assertSame("clear no funciona bien", arbol.size(), 0);
	}	
	
	/**
	 * Comprueba el correcto funcionamiento del método contains en una estructura de arbol.
	 * Para ello va probando las diferentes situaciones en las que se puede llamar a la funcion esperando el resultado correcto.
	 */
	@Test
	public void containsTest(){
		//cuando el arbol esta vacio no contiene nada
		assertFalse("contains no funciona bien", arbol.contains(4));
		assertFalse("contains no funciona bien", arbol.contains(2));
		assertFalse("contains no funciona bien", arbol.contains(55));
		
		//cuando el arbol solo tiene un nodo raiz solo contiene un valor
		arbol.add(3);
		assertTrue("contains no funciona bien", arbol.contains(3));
		assertFalse("contains no funciona bien", arbol.contains(4));
		assertFalse("contains no funciona bien", arbol.contains(2));
		assertFalse("contains no funciona bien", arbol.contains(55));
	
		
		//con rotaciones
		arbol.add(4);
		arbol.add(6);
		assertTrue("contains no funciona bien", arbol.contains(4));
		assertTrue("contains no funciona bien", arbol.contains(6));
		assertTrue("contains no funciona bien", arbol.contains(3));
		
		arbol.remove(4);
		arbol.remove(6);
		assertFalse("contains no funciona bien", arbol.contains(6));
		assertFalse("contains no funciona bien", arbol.contains(4));
		
		arbol.add(2);
		arbol.add(1);
		assertTrue("contains no funciona bien", arbol.contains(1));
		assertTrue("contains no funciona bien", arbol.contains(2));
	}
	
	/**
	 * Comprueba el correcto funcionamiento del método containsAll en una estructura de arbol.
	 * Para ello va probando las diferentes situaciones en las que se puede llamar a la funcion esperando el resultado correcto.
	 */
	@Test
	public void containsAllTest(){
		Collection<Integer> col = new ArrayList<Integer>();
		Collection<Integer> col3 = new ArrayList<Integer>();
		col.add(4);
		col.add(5);
		col.add(6);
		
		//cuando el arbol esta vacio no contiene nada
		assertFalse("containsAll no funciona bien", arbol.containsAll(col));
		
		//cuando se trata de un arbol raiz, contiene la raiz
		arbol.add(2);
		col3.add(2);
		assertTrue("containsAll no funciona bien", arbol.containsAll(col3));
		
		//comprobamos que cuando el arbol NO contiene todos los elementos de la lista, devuelve false.
		arbol.add(4);
		arbol.add(5);
		assertFalse("containsAll no funciona bien", arbol.containsAll(col));
		
		//comprobamos que cuando contiene a todos devuelve true
		arbol.add(6);
		assertTrue("containsAll no funciona bien", arbol.containsAll(col));
		
		//si la colección está vacia devuelve true
		Collection<Integer> col2 = new ArrayList<Integer>();
		assertTrue("containsAll no funciona bien", arbol.containsAll(col2));
	}
	
	/**
	 * Comprueba el correcto funcionamiento del método isEmpty en una estructura de arbol.
	 * Para ello va probando las diferentes situaciones en las que se puede llamar a la funcion esperando el resultado correcto.
	 */
	@Test
	public void isEmptyTest(){
		//el arbol vacio estará vacio
		assertTrue("isEmpty no funciona bien", arbol.isEmpty());
		
		//el arbol raiz no estara vacio
		arbol.add(3);
		assertFalse("isEmpty no funciona bien", arbol.isEmpty());
		
		arbol.add(4);
		arbol.add(2);
		assertFalse("isEmpty no funciona bien", arbol.isEmpty());
	}
	
	/**
	 * Comprueba el correcto funcionamiento del método iterator en una estructura de arbol.
	 * Para ello va probando las diferentes situaciones en las que se puede llamar a la funcion esperando el resultado correcto.
	 */
	@Test
	public void iterator(){
		Iterator<Integer> iter = null;
		Iterator<Integer> iter2 = null;
		
		//el arbol vacio devuelve un iterator vacio
		iter = arbol.iterator();
		assertFalse("iterator no funciona bien", iter.hasNext());
		
		//el arbol raiz devuelve un iterator con un elemento
		arbol.add(4);
		iter = arbol.iterator();
		assertTrue("iterator no funciona bien", iter.hasNext());
		iter.next();
		assertFalse("iterator no funciona bien", iter.hasNext());
		
		//el arbol con 2 elementos devuelve un iterator con 2 elementos
		arbol.add(5);
		iter = arbol.iterator();
		for(int i = 0; i< arbol.size(); i++){
			assertTrue("iterator no funciona bien", iter.hasNext());
			iter.next();
		}
		assertFalse("iterator no funciona bien", iter.hasNext());
		
		//el arbol con 7 elementos devolverá un iterator con 6 elementos
		arbol.add(7);
		arbol.add(10);
		arbol.add(2);
		arbol.add(30);
		arbol.add(12);
		iter = arbol.iterator();
		for(int i = 0; i< arbol.size(); i++){
			assertTrue("iterator no funciona bien", iter.hasNext());
			iter.next();
		}
		assertFalse("iterator no funciona bien", iter.hasNext());
	}
	
	/**
	 * Comprueba el correcto funcionamiento del método remove en una estructura de arbol.
	 * Para ello va probando las diferentes situaciones en las que se puede llamar a la funcion esperando el resultado correcto.
	 */
	@Test
	public void removeTest(){
		//borrando un elemento en un arbol vacio devuelve false
		assertFalse("remove no funciona bien", arbol.remove(4));
		
		//borrar un elemento inexistente en un arbol vacio devuelve false
		arbol.add(30);
		assertFalse("remove no funciona bien", arbol.remove(4));
		
		//borrarando la raiz de un arbol raiz obtenemos un true
		assertTrue("remove no funciona bien", arbol.remove(30));
		assertFalse("remove no funciona bien", arbol.contains(30));
		
		//borramos la raiz de un arbol con mas de un nodo
		arbol.add(20);
		arbol.add(35);
		arbol.add(21);
		assertTrue("remove no funciona bien", arbol.remove(35));
		assertFalse("remove no funciona bien", arbol.contains(35));
		
		//si borramos un elemento cualquiera del arbol obtenemos true y el arbol no contendrá ya ese elemento
		arbol.add(30);
		arbol.add(20);
		arbol.add(40);
		arbol.add(45);
		arbol.add(38);
		assertTrue("remove no funciona bien", arbol.remove(38));
		assertTrue("remove no funciona bien", arbol.remove(40));
		assertFalse("remove no funciona bien", arbol.contains(38));
		assertFalse("remove no funciona bien", arbol.contains(40));
		
		//el nodo a borrar solo tiene un nivel bajo él
		arbol.remove(30);
		arbol.remove(20);
		arbol.remove(45);
		arbol.remove(21);
		arbol.remove(20);
		
		arbol.add(21);
		arbol.add(30);
		arbol.add(18);
		arbol.add(34);
		arbol.add(25);
		assertTrue("remove no funciona bien", arbol.remove(30));
		
		//comprobamos que reestructura bien con rotacion simple izquierda
		assertTrue("remove no funciona bien", arbol.remove(18));
		assertTrue("remove no funciona bien", arbol.preOrden().get(0)==25);
		
		//comprobamos que reestructura bien con rotacion simple derecha
		arbol.add(18);
		assertTrue("remove no funciona bien", arbol.remove(34));
		assertTrue("remove no funciona bien", arbol.preOrden().get(0)==21);
		
		//comprobamos que reestructura bien con rotacion compuesta derecha-izquierda 
		arbol.add(23);
		arbol.add(30);
		arbol.add(15);
		arbol.add(22);
		assertTrue("remove no funciona bien", arbol.remove(15));
		assertTrue("remove no funciona bien", arbol.preOrden().get(0)==23);
		
		//comprobamos que reestructura bien con rotacion compuesta izquierda-derecha
		arbol.remove(18);
		arbol.remove(21);
		arbol.remove(22);
		arbol.remove(23);
		arbol.add(10);
		arbol.add(30);
		arbol.add(35);
		arbol.add(8);
		arbol.add(15);
		arbol.add(12);
		assertTrue("remove no funciona bien", arbol.preOrden().get(0)==25);
		assertTrue("remove no funciona bien", arbol.remove(35));
		assertTrue("remove no funciona bien", arbol.preOrden().get(0)==15);		
	}
	
	/**
	 * Comprueba el correcto funcionamiento del método removeAll en una estructura de arbol.
	 * Para ello va probando las diferentes situaciones en las que se puede llamar a la funcion esperando el resultado correcto.
	 */
	@Test
	public void removeAllTest(){
		Collection<Integer> col = new ArrayList<Integer>();
		Collection<Integer> col2 = new ArrayList<Integer>();
		col.add(4);
		
		//devuelve false cuando el arbol esta vacio e intenta borrar algo
		assertFalse("removeAll no funciona bien", arbol.removeAll(col));
		
		//devuelve false cuando la colleccion que le pasa esta vacia
		col.removeAll(col);
		assertFalse("removeAll no funciona bien", arbol.removeAll(col));
		
		//devuelve false cuando el intenta borrar algo que no existe en el arbol
		arbol.add(5);
		assertFalse("removeAll no funciona bien", arbol.removeAll(col));
		
		//devuelve true cuando borra la raiz solamente
		col.remove(4);
		col.add(5);
		assertTrue("removeAll no funciona bien", arbol.removeAll(col));
		
		//cuando el arbol es modificado tas la llamada a la funcion devolverá true
		col.add(7);
		col.add(9);
		col.add(1);
		col.add(24);
		
		arbol.add(9);
		assertTrue("removeAll no funciona bien", arbol.removeAll(col));
		
		//cuando ningun elemento de la coleccion esta en el arbol devuelve false
		col.removeAll(col);
		col.add(200);
		assertFalse("removeAll no funciona bien", arbol.removeAll(col));
		col.add(150);
		col.add(245);
		assertFalse("removeAll no funciona bien", arbol.removeAll(col));
		
	}
	
	/**
	 * Comprueba el correcto funcionamiento del método retainAll en una estructura de arbol.
	 * Para ello va probando las diferentes situaciones en las que se puede llamar a la funcion esperando el resultado correcto.
	 */
	@Test
	public void retainAllTest(){
		Collection<Integer> col = new ArrayList<Integer>();
		col.add(10);
		col.add(5);
		
		//si se le pasa una colleccion llena devolverá false si no existia arbol
		assertFalse("retainAll no funciona bien", arbol.retainAll(col));
		
		//si se le pasa una colleccion vacia devolverá false si no existia arbol
		col.removeAll(col);
		assertFalse("retainAll no funciona bien", arbol.retainAll(col));
		
		//si se le pasa una colleccion vacia devolverá true si existía el arbol porque lo borra todo
		arbol.add(100);
		arbol.add(10);
		arbol.add(1);
		assertTrue("retainAll no funciona bien", arbol.retainAll(col));
		assertSame("retainAll no funciona bien", arbol.size(), 0); //el arbol queda vacio
		
		//cuando borra todo del arbol con una colleccion con elementos
		col.add(5);
		col.add(2);
		col.add(8);
		arbol.add(100);
		arbol.add(200);
		arbol.add(300);
		
		assertTrue("retainAll no funciona bien", arbol.retainAll(col));
		
		//cuando NO borra nada del arbol con una colleccion con elementos
		col.removeAll(col);
		col.add(100);
		col.add(200);
		col.add(300);
		
		arbol.add(100);
		arbol.add(200);
		arbol.add(300);
		assertFalse("retainAll no funciona bien", arbol.retainAll(col));
		
		//cuando borra parte del arbol
		arbol.add(500);
		arbol.add(400);
		assertTrue("retainAll no funciona bien", arbol.retainAll(col));
	}
	
	/**
	 * Comprueba el correcto funcionamiento del método size en una estructura de arbol.
	 * Para ello va probando las diferentes situaciones en las que se puede llamar a la funcion esperando el resultado correcto.
	 */
	@Test
	public void sizeTest(){
		//el arbol vacio tendra size 0 
		assertSame("size no funciona bien", arbol.size(), 0);
		
		//el arbol raiz tamaño 1
		arbol.add(5);
		assertSame("size no funciona bien", arbol.size(), 1);
		
		//el arbol con reestructuracion simple izquierda
		arbol.add(6);
		arbol.add(7);
		assertSame("size no funciona bien", arbol.size(), 3);
		
		//el arbol con reestructuracion simpe derecha
		arbol.remove(6);
		arbol.remove(7);
		arbol.add(4);
		arbol.add(3);
		assertSame("size no funciona bien", arbol.size(), 3);
	}
	
	/**
	 * Comprueba el correcto funcionamiento del método inOrden en una estructura de arbol.
	 * Para ello va probando las diferentes situaciones en las que se puede llamar a la funcion esperando el resultado correcto.
	 */
	@Test
	public void inOrdenTest(){
		//el arbol vacio tendra una lista vacia
		assertSame("inOrden no funciona bien", arbol.inOrden().size(), 0);
		
		//el arbol raiz con tamaño 1 y con el primer elemento igual a la raiz
		arbol.add(5);
		assertSame("inOrden no funciona bien", arbol.inOrden().size(), 1);
		assertSame("inOrden no funciona bien", arbol.inOrden().get(0), 5);
		
		//comprobamos que los devuelve en el orden correcto
		arbol.add(2);
		arbol.add(7);
		arbol.add(1);
		arbol.add(3);
		arbol.add(6);
		arbol.add(9);
		arbol.add(8);
		arbol.add(10);
		assertSame("inOrden no funciona bien", arbol.inOrden().get(0), 1);
		assertSame("inOrden no funciona bien", arbol.inOrden().get(1), 2);
		assertSame("inOrden no funciona bien", arbol.inOrden().get(2), 3);
		assertSame("inOrden no funciona bien", arbol.inOrden().get(3), 5);
		assertSame("inOrden no funciona bien", arbol.inOrden().get(4), 6);
		assertSame("inOrden no funciona bien", arbol.inOrden().get(5), 7);
		assertSame("inOrden no funciona bien", arbol.inOrden().get(6), 8);
		assertSame("inOrden no funciona bien", arbol.inOrden().get(7), 9);
		assertSame("inOrden no funciona bien", arbol.inOrden().get(8), 10);	
			
		//con rotacion simple izquierda		
		arbol2 = new ArbolAVL<Integer>();
		arbol2.add(2);
		arbol2.add(3);
		arbol2.add(4);
		assertSame("inOrden no funciona bien", arbol2.inOrden().get(0), 2);
		assertSame("inOrden no funciona bien", arbol2.inOrden().get(1), 3);
		assertSame("inOrden no funciona bien", arbol2.inOrden().get(2), 4);
		
		//con rotacion simple izquierda
		arbol3 = new ArbolAVL<Integer>();
		arbol3.add(4);
		arbol3.add(3);
		arbol3.add(1);
		assertSame("inOrden no funciona bien", arbol3.inOrden().get(0), 1);
		assertSame("inOrden no funciona bien", arbol3.inOrden().get(1), 3);
		assertSame("inOrden no funciona bien", arbol3.inOrden().get(2), 4);
		
		//con rotacion compuesta derecha-izquierda
		arbol4 = new ArbolAVL<Integer>();
		arbol4.add(5);
		arbol4.add(3);
		arbol4.add(10);
		arbol4.add(11);
		arbol4.add(9);
		arbol4.add(8);
		assertSame("inOrden no funciona bien", arbol4.inOrden().get(0), 3);
		assertSame("inOrden no funciona bien", arbol4.inOrden().get(1), 5);
		assertSame("inOrden no funciona bien", arbol4.inOrden().get(2), 8);
		assertSame("inOrden no funciona bien", arbol4.inOrden().get(3), 9);
		assertSame("inOrden no funciona bien", arbol4.inOrden().get(4), 10);
		assertSame("inOrden no funciona bien", arbol4.inOrden().get(5), 11);
		
		//con rotacion compuesta derecha-izquierda
		arbol5 = new ArbolAVL<Integer>();
		arbol5.add(10);
		arbol5.add(7);
		arbol5.add(12);
		arbol5.add(6);
		arbol5.add(8);
		arbol5.add(9);
		assertSame("inOrden no funciona bien", arbol5.inOrden().get(0), 6);
		assertSame("inOrden no funciona bien", arbol5.inOrden().get(1), 7);
		assertSame("inOrden no funciona bien", arbol5.inOrden().get(2), 8);
		assertSame("inOrden no funciona bien", arbol5.inOrden().get(3), 9);
		assertSame("inOrden no funciona bien", arbol5.inOrden().get(4), 10);
		assertSame("inOrden no funciona bien", arbol5.inOrden().get(5), 12);
	}
	
	/**
	 * Comprueba el correcto funcionamiento del método preOrden en una estructura de arbol.
	 * Para ello va probando las diferentes situaciones en las que se puede llamar a la funcion esperando el resultado correcto.
	 */
	@Test
	public void preOrdenTest(){
		//el arbol vacio tendra una lista vacia
		assertSame("preOrden no funciona bien", arbol.preOrden().size(), 0);
		
		//el arbol raiz con tamaño 1 y con el primer elemento igual a la raiz
		arbol.add(5);
		assertSame("preOrden no funciona bien", arbol.preOrden().size(), 1);
		assertSame("preOrden no funciona bien", arbol.preOrden().get(0), 5);
		
		//comprobamos que los devuelve en el orden correcto
		arbol.add(2);
		arbol.add(7);
		arbol.add(1);
		arbol.add(3);
		arbol.add(6);
		arbol.add(9);
		arbol.add(8);
		arbol.add(10);
		assertSame("preOrden no funciona bien", arbol.preOrden().get(0), 5);
		assertSame("preOrden no funciona bien", arbol.preOrden().get(1), 2);
		assertSame("preOrden no funciona bien", arbol.preOrden().get(2), 1);
		assertSame("preOrden no funciona bien", arbol.preOrden().get(3), 3);
		assertSame("preOrden no funciona bien", arbol.preOrden().get(4), 7);
		assertSame("preOrden no funciona bien", arbol.preOrden().get(5), 6);
		assertSame("preOrden no funciona bien", arbol.preOrden().get(6), 9);
		assertSame("preOrden no funciona bien", arbol.preOrden().get(7), 8);
		assertSame("preOrden no funciona bien", arbol.preOrden().get(8), 10);	
			
		//con rotacion simple izquierda		
		arbol2 = new ArbolAVL<Integer>();
		arbol2.add(2);
		arbol2.add(3);
		arbol2.add(4);
		assertSame("preOrden no funciona bien", arbol2.preOrden().get(0), 3);
		assertSame("preOrden no funciona bien", arbol2.preOrden().get(1), 2);
		assertSame("preOrden no funciona bien", arbol2.preOrden().get(2), 4);
		
		//con rotacion simple izquierda
		arbol3 = new ArbolAVL<Integer>();
		arbol3.add(4);
		arbol3.add(3);
		arbol3.add(1);
		assertSame("preOrden no funciona bien", arbol3.preOrden().get(0), 3);
		assertSame("preOrden no funciona bien", arbol3.preOrden().get(1), 1);
		assertSame("preOrden no funciona bien", arbol3.preOrden().get(2), 4);
		
		//con rotacion compuesta derecha-izquierda
		arbol4 = new ArbolAVL<Integer>();
		arbol4.add(5);
		arbol4.add(3);
		arbol4.add(10);
		arbol4.add(11);
		arbol4.add(9);
		arbol4.add(8);
		assertSame("preOrden no funciona bien", arbol4.preOrden().get(0), 9);
		assertSame("preOrden no funciona bien", arbol4.preOrden().get(1), 5);
		assertSame("preOrden no funciona bien", arbol4.preOrden().get(2), 3);
		assertSame("preOrden no funciona bien", arbol4.preOrden().get(3), 8);
		assertSame("preOrden no funciona bien", arbol4.preOrden().get(4), 10);
		assertSame("preOrden no funciona bien", arbol4.preOrden().get(5), 11);
		
		//con rotacion compuesta derecha-izquierda
		arbol5 = new ArbolAVL<Integer>();
		arbol5.add(10);
		arbol5.add(7);
		arbol5.add(12);
		arbol5.add(6);
		arbol5.add(8);
		arbol5.add(9);
		assertSame("preOrden no funciona bien", arbol5.preOrden().get(0), 8);
		assertSame("preOrden no funciona bien", arbol5.preOrden().get(1), 7);
		assertSame("preOrden no funciona bien", arbol5.preOrden().get(2), 6);
		assertSame("preOrden no funciona bien", arbol5.preOrden().get(3), 10);
		assertSame("preOrden no funciona bien", arbol5.preOrden().get(4), 9);
		assertSame("preOrden no funciona bien", arbol5.preOrden().get(5), 12);
	}
	
	/**
	 * Comprueba el correcto funcionamiento del método postOrden en una estructura de arbol.
	 * Para ello va probando las diferentes situaciones en las que se puede llamar a la funcion esperando el resultado correcto.
	 */
	@Test
	public void postOrdenTest(){
		//el arbol vacio tendra una lista vacia
		assertSame("preOrden no funciona bien", arbol.postOrden().size(), 0);
		
		//el arbol raiz con tamaño 1 y con el primer elemento igual a la raiz
		arbol.add(5);
		assertSame("postOrden no funciona bien", arbol.postOrden().size(), 1);
		assertSame("postOrden no funciona bien", arbol.postOrden().get(0), 5);
		
		//comprobamos que los devuelve en el orden correcto
		arbol.add(2);
		arbol.add(7);
		arbol.add(1);
		arbol.add(3);
		arbol.add(6);
		arbol.add(9);
		arbol.add(8);
		arbol.add(10);
		assertSame("postOrden no funciona bien", arbol.postOrden().get(0), 1);
		assertSame("postOrden no funciona bien", arbol.postOrden().get(1), 3);
		assertSame("postOrden no funciona bien", arbol.postOrden().get(2), 2);
		assertSame("postOrden no funciona bien", arbol.postOrden().get(3), 6);
		assertSame("postOrden no funciona bien", arbol.postOrden().get(4), 8);
		assertSame("postOrden no funciona bien", arbol.postOrden().get(5), 10);
		assertSame("postOrden no funciona bien", arbol.postOrden().get(6), 9);
		assertSame("postOrden no funciona bien", arbol.postOrden().get(7), 7);
		assertSame("postOrden no funciona bien", arbol.postOrden().get(8), 5);	
			
		//con rotacion simple izquierda		
		arbol2 = new ArbolAVL<Integer>();
		arbol2.add(2);
		arbol2.add(3);
		arbol2.add(4);
		assertSame("postOrden no funciona bien", arbol2.postOrden().get(0), 2);
		assertSame("postOrden no funciona bien", arbol2.postOrden().get(1), 4);
		assertSame("postOrden no funciona bien", arbol2.postOrden().get(2), 3);
		
		//con rotacion simple izquierda
		arbol3 = new ArbolAVL<Integer>();
		arbol3.add(4);
		arbol3.add(3);
		arbol3.add(1);
		assertSame("postOrden no funciona bien", arbol3.postOrden().get(0), 1);
		assertSame("postOrden no funciona bien", arbol3.postOrden().get(1), 4);
		assertSame("postOrden no funciona bien", arbol3.postOrden().get(2), 3);
		
		//con rotacion compuesta derecha-izquierda
		arbol4 = new ArbolAVL<Integer>();
		arbol4.add(5);
		arbol4.add(3);
		arbol4.add(10);
		arbol4.add(11);
		arbol4.add(9);
		arbol4.add(8);
		assertSame("postOrden no funciona bien", arbol4.postOrden().get(0), 3);
		assertSame("postOrden no funciona bien", arbol4.postOrden().get(1), 8);
		assertSame("postOrden no funciona bien", arbol4.postOrden().get(2), 5);
		assertSame("postOrden no funciona bien", arbol4.postOrden().get(3), 11);
		assertSame("postOrden no funciona bien", arbol4.postOrden().get(4), 10);
		assertSame("postOrden no funciona bien", arbol4.postOrden().get(5), 9);
		
		//con rotacion compuesta derecha-izquierda
		arbol5 = new ArbolAVL<Integer>();
		arbol5.add(10);
		arbol5.add(7);
		arbol5.add(12);
		arbol5.add(6);
		arbol5.add(8);
		arbol5.add(9);
		assertSame("postOrden no funciona bien", arbol5.postOrden().get(0), 6);
		assertSame("postOrden no funciona bien", arbol5.postOrden().get(1), 7);
		assertSame("postOrden no funciona bien", arbol5.postOrden().get(2), 9);
		assertSame("postOrden no funciona bien", arbol5.postOrden().get(3), 12);
		assertSame("postOrden no funciona bien", arbol5.postOrden().get(4), 10);
		assertSame("postOrden no funciona bien", arbol5.postOrden().get(5), 8);
	}
	
	/**
	 * Comprueba el correcto funcionamiento del método altura en una estructura de arbol.
	 * Para ello va probando las diferentes situaciones en las que se puede llamar a la funcion esperando el resultado correcto.
	 */
	@Test
	public void altura(){
		//altura de un nodo inexistente -1
		assertSame("altura no funciona bien", arbol.altura(4),-1);
		assertSame("altura no funciona bien", arbol.altura(8),-1);
		
		//altura de un nodo hoja, 1
		arbol.add(5);
		assertSame("altura no funciona bien", arbol.altura(5),1);
		
		//la altura de un nodo con un nivel de hijos, 2
		arbol.add(7);
		assertSame("altura no funciona bien", arbol.altura(5),2);
		arbol.add(4);
		assertSame("altura no funciona bien", arbol.altura(5),2);
		
		//La altura de 3
		arbol.add(8);
		arbol.add(6);
		assertSame("altura no funciona bien", arbol.altura(5),3);
	}
	
	/**
	 * Comprueba el correcto funcionamiento del método profundidad en una estructura de arbol.
	 * Para ello va probando las diferentes situaciones en las que se puede llamar a la funcion esperando el resultado correcto.
	 */
	@Test
	public void profundidadTest(){
		//la del nodo raiz es 0
		arbol.add(10);
		assertSame("profundidad no funciona bien", arbol.profundidad(10), 0);
		
		//la un nodo hoja hijo de raiz es 1
		arbol.add(20);
		assertSame("profundidad no funciona bien", arbol.profundidad(20), 1);
		arbol.add(5);
		assertSame("profundidad no funciona bien", arbol.profundidad(5), 1);
		assertSame("profundidad no funciona bien", arbol.profundidad(10), 0);
		
		//tras una rotacion
		arbol.remove(5);
		arbol.add(30);
		assertSame("profundidad no funciona bien", arbol.profundidad(20), 0);
		assertSame("profundidad no funciona bien", arbol.profundidad(10), 1);
		assertSame("profundidad no funciona bien", arbol.profundidad(30), 1);
		
		arbol.add(40);
		arbol.add(50);
		assertSame("profundidad no funciona bien", arbol.profundidad(40), 1);
		assertSame("profundidad no funciona bien", arbol.profundidad(50), 2);
		assertSame("profundidad no funciona bien", arbol.profundidad(30), 2);

		//tras remove
		arbol.add(9);
		arbol.add(35);
		arbol.remove(9);
		assertSame("profundidad no funciona bien", arbol.profundidad(35), 2);
		assertSame("profundidad no funciona bien", arbol.profundidad(50), 2);
		assertSame("profundidad no funciona bien", arbol.profundidad(20), 1);
		assertSame("profundidad no funciona bien", arbol.profundidad(10), 2);
	}
	
	/**
	 * Comprueba el correcto funcionamiento del método getRaiz en una estructura de arbol.
	 * Para ello va probando las diferentes situaciones en las que se puede llamar a la funcion esperando el resultado correcto.
	 */
	@Test
	public void getRaizTest(){
		//la raiz de un arbol vacio es null
		assertSame("getRaiz no funciona bien", arbol.getRaiz(), null);
		
		//la raiz de una arbol raiz es su nodo
		arbol.add(5);
		assertSame("getRaiz no funciona bien", arbol.preOrden().get(0), 5);
		
		//la raiz tras insertar sigue sinedo ella a no ser que haya rotaciones
		arbol.add(4);
		arbol.add(6);
		assertSame("getRaiz no funciona bien", arbol.preOrden().get(0), 5);
		
		//con rotacion simple derecha
		arbol.remove(6);
		arbol.add(3);
		assertSame("getRaiz no funciona bien", arbol.preOrden().get(0), 4);
		
		//con rotacion simple izquierda
		arbol.remove(3);
		arbol.add(6);
		assertSame("getRaiz no funciona bien", arbol.preOrden().get(0), 5);
		
		//con rotacion compuesta derecha-izquierda
		arbol4 = new ArbolAVL<Integer>();
		arbol4.add(5);
		arbol4.add(3);
		arbol4.add(10);
		arbol4.add(11);
		arbol4.add(9);
		arbol4.add(8);
		assertSame("getRaiz no funciona bien", arbol4.preOrden().get(0), 9);

		
		//con rotacion compuesta derecha-izquierda
		arbol5 = new ArbolAVL<Integer>();
		arbol5.add(10);
		arbol5.add(7);
		arbol5.add(12);
		arbol5.add(6);
		arbol5.add(8);
		arbol5.add(9);
		assertSame("getRaiz no funciona bien", arbol5.preOrden().get(0), 8);	
	}
	
	/**
	 * Comprueba el correcto funcionamiento del método getNodo en una estructura de arbol.
	 * Para ello va probando las diferentes situaciones en las que se puede llamar a la funcion esperando el resultado correcto.
	 */
	@Test
	public void getNodoTest(){
		//con el arbol vacio devuelve null
		assertSame("getNodo no funciona bien", arbol.getNodo(8), null);	
			
		//el arbol raiz nos devuelve la raiz
		arbol.add(8);
		assertSame("getNodo no funciona bien", arbol.extraeDato(arbol.getNodo(8)), 8);	
			
		//con un nodo intermedio 
		arbol.add(6);
		arbol.add(10);
		assertSame("getNodo no funciona bien", arbol.extraeDato(arbol.getNodo(6)), 6);	
	}
	
	
}
    
