package es.ubu.lsi.util;
import java.util.*;

/**
 * Implementa métodos de AbstractCollection y AbstractSet de forma específica para 
 * una estructura de arbol AVL, mejorando en ciertos casos la velocidad de ejecucion.
 *
 * @author Alvaro Trigo Lopez
 *
 * @version 5.00 2009/6/14
 */
public class ArbolAVL<T> extends java.util.AbstractSet<T>{
    /**
     * Nodo raiz del arbol.
     */
    private Nodo<T> raiz;
    
    /**
     * Comparador.
     */
    Comparator<T> comparador;
    
    /**
     * Constructor por defecto.
     */
    public ArbolAVL(){	
    }
    /**
     * Constructor para recibir el comparador que se utilizará para comparar los elementos. Si
     * no se construye el árbol con este método los objetos se compararan en función del orden
     * natural (comparable).
     * @param cmp comparador
     */
    public ArbolAVL(Comparator<T> cmp){	
    	this.comparador = cmp;
    }

    /// ===============================================/// 
    ///             Abstract collection                ///
    ///                                                ///
    /// ===============================================///
    
    /**
     * Ensures that this collection contains the specified element (optional operation). Returns true if this collection changed as a result of the call. (Returns false if this collection does not permit duplicates and already contains the specified element.)
     * Collections that support this operation may place limitations on what elements may be added to this collection. In particular, some collections will refuse to add null elements, and others will impose restrictions on the type of elements that may be added. Collection classes should clearly specify in their documentation any restrictions on what elements may be added.
     * If a collection refuses to add a particular element for any reason other than that it already contains the element, it must throw an exception (rather than returning false). This preserves the invariant that a collection always contains the specified element after this call returns.
     * This implementation always throws an UnsupportedOperationException. 
     *
     * @param e - element whose presence in this collection is to be ensured.
     * @return true if this collection changed as a result of the call.
     * @throws ClassCastException - if the class of the specified element prevents it from being added to this collection 
     * @throws NullPointerException - if the specified element is null and this collection does not permit null elements 
     * @throws IllegalStateException - if the element cannot be added at this time due to insertion restrictions
     */
    public boolean add(T e) throws ClassCastException, NullPointerException, IllegalStateException{
    	Nodo<T> nodo = new Nodo<T>(e);
    	boolean salir = false;
    	boolean der = false;
    	Nodo<T> raizTmp = this.getRaiz();

    	int altIzq, altDer;

    	//no existía arbol
    	if(raizTmp == null){
    		this.raiz = nodo;
    		return true;
    	}else
    	
    	//estaba ya en el arbol?
    	if(this.contains(nodo.getDato())){
    		return false;
    	}
    	
    	//no estaba antes en el arbol
    	else{    	
    		while(!salir){

    			//es mayor el nodo a insertar que la raiz?    				
		    	if(this.compararDato(nodo.getDato(), raizTmp.getDato())>0){
		    		if(raizTmp.getDerecha()!=null){
		    			raizTmp = raizTmp.getDerecha();	
		    		}else{
		    			salir = true;
		    			der = true;
		    		}
		    			    		
		    	}
		    	//el nodo es menor que la raiz
		    	else{
		    		if(raizTmp.getIzquierda()!=null){
		    			raizTmp = raizTmp.getIzquierda();
		    		}else{
		    			salir = true;
		    		}
		    	}
    		}
    		
    		//tengo que insertarlo a la derecha?
    		if(der){
    			raizTmp.setDerecha(nodo);
    		}
    		
    		//lo inserto a la izquierda
    		else{
    			raizTmp.setIzquierda(nodo);
    		}
	
    		//mientras no este equilibrado el arbol	miramos donde reestructurar
    		while(equilibrado(this.getRaiz())<0){
				raizTmp = padre(raizTmp);
    		
    			if(raizTmp.getDerecha()==null){
	    			altDer = 0;
	    		}else{
	    			altDer = raizTmp.getDerecha().getAltura();
	    		}
	    		
	    		if(raizTmp.getIzquierda()==null){
	    			altIzq = 0;
	    		}else{
	    			altIzq = raizTmp.getIzquierda().getAltura();
	    		}
	    		
    			Nodo<T> cambiar = estructurar(raizTmp, altIzq, altDer);
    			Nodo<T> superior = padre(raizTmp);
	
    			//si los nodos modificados tenian un padre anteriormente
    			if(compararDato(superior.getDato(), raizTmp.getDato())!=0){
    				if(superior.getIzquierda()!=null && compararDato(superior.getIzquierda().getDato(), raizTmp.getDato())==0){
	    				superior.setIzquierda(cambiar);		
		    		}
		    		else if(superior.getDerecha()!=null && compararDato(superior.getDerecha().getDato(), raizTmp.getDato())==0){
	    				superior.setDerecha(cambiar);
	    			}
    			}else{
    				this.raiz = cambiar;
    			}
    		}
    		return true;
    	}
    }
    
    /**
     * Estructura el arbol haciendo las rotaciones necesarias.
     * Rotacion simple derecha, rotacion compuesta derecha izquierda, rotacion simple
     * izquierda y rotacion compuesta izquierda derecha.
     * @param nodo nodo con factor de equilibro 2.
     * @param altIzq altura izquierda del nodo pasado por parametro.
     * @param altDer altura derecha del nodo pasado por parametro.
     *
     * @return nodo nodo que pasa a ser la raiz del subarbol estructurado.
     */
    private Nodo<T> estructurar(Nodo<T> nodo, int altIzq, int altDer){
		if(extraeFactorE(nodo)==2){
			if( extraeFactorE(nodo.getDerecha() )==1  || extraeFactorE(nodo.getDerecha()) == 0){
				nodo = rotacionSimpleIzquierda(nodo);
			}
			
			else if(extraeFactorE(nodo.getDerecha() )==-1){
				nodo = rotacionCompuestaDerecha(nodo);
			}
		}
		else if(extraeFactorE(nodo)==-2){
			if(extraeFactorE(nodo.getIzquierda() )==-1 || extraeFactorE(nodo.getDerecha())==0){
				nodo = rotacionSimpleDerecha(nodo);
			}
			
			else if(extraeFactorE(nodo.getIzquierda())==1){
				nodo = rotacionCompuestaIzquierda(nodo);
			}
		}

		return nodo;	
    }
    
    /**
     * Extrae el factor de equilibrio del nodo pasado por parametro.
     * @param nodo nodo del que se desea extraer el factor de equilibrio.
     * 
     * @return factor de equilibrio.
     */
    public int extraeFactorE(Nodo<T> nodo){
    	if(nodo!=null){
    		return nodo.getFactorE();
    	}else{
    		return 0;
    	}
    }

	/**
	 * Realiza la operacion de rotacion simple izquierda en el subarbol 
	 * que tiene como raiz el nodo pasado por parametro.
	 * @param nodo raiz del subarbol a rotar.
	 *
	 * @return nodo nodo que pasa a ser la raiz del subarbol estructurado.
	 */
    public Nodo<T> rotacionSimpleIzquierda(Nodo<T> nodo){
		Nodo<T> nodoTmp = nodo;
		
    	nodo = nodoTmp.getDerecha(); //clone??
		nodoTmp.setDerecha(nodo.getIzquierda());

		nodo.setIzquierda(nodoTmp);

		return nodo;
    }

	/**
	 * Realiza la operacion de rotacion simple derecha en el subarbol .
	 * que tiene como raiz el nodo pasado por parametro.
	 * @param nodo raiz del subarbol a rotar.
	 *
	 * @return nodo nodo que pasa a ser la raiz del subarbol estructurado.
	 */
    public Nodo<T> rotacionSimpleDerecha(Nodo<T> nodo){
    	Nodo<T> nodoTmp = nodo;
    	nodo = nodoTmp.getIzquierda();

		nodoTmp.setIzquierda(nodo.getDerecha());
		nodo.setDerecha(nodoTmp);

		return nodo;
    }

	/**
	 * Realiza la operacion de rotacion compeusta izquierda-derecha en el subarbol .
	 * que tiene como raiz el nodo pasado por parametro.
	 * @param nodo raiz del subarbol a rotar.
	 *
	 * @return nodo nodo que pasa a ser la raiz del subarbol estructurado.
	 */
    public Nodo<T> rotacionCompuestaIzquierda(Nodo<T> nodo){
    	Nodo<T> nodoTmp = nodo; //57

        nodoTmp = rotacionSimpleIzquierda(nodoTmp.getIzquierda()); //param 42 | sale: 54
        
		nodo.setIzquierda(nodoTmp); //param 54

		nodoTmp = rotacionSimpleDerecha(nodo); //param 54  | sale: 54
		
		return nodoTmp;
    }

	/**
	 * Realiza la operacion de rotacion compuesta derecha-izquierda en el subarbol 
	 * que tiene como raiz el nodo pasado por parametro.
	 * @param nodo raiz del subarbol a rotar.
	 *
	 * @return nodo nodo que pasa a ser la raiz del subarbol estructurado.
	 */
    public Nodo<T> rotacionCompuestaDerecha(Nodo<T> nodo){
    	Nodo<T> nodoTmp = nodo;
    	
        nodoTmp = rotacionSimpleDerecha(nodoTmp.getDerecha());
	
		nodo.setDerecha(nodoTmp);

		nodoTmp= rotacionSimpleIzquierda(nodo);

		return nodoTmp;
    }

	/**
	 * Indica si el arbol o subarbol cuya raiz es es el nodo pasado por parametro
	 * es un arbol equilibrado o no.
	 * @param n raiz del subarbol o arbol .
	 *
	 * @return -1 si no esta equilibrado. La altura en caso contrario.
	 */
	public int equilibrado(Nodo<T> n){
		int hIzq = 0;
		int hDer = 0;
		
		if(n==null){
    		return 0;
    	}
    	
    	hIzq = equilibrado(n.getIzquierda());
    	
    	if(hIzq < 0){
    		return hIzq;
    	}
    	
    	hDer = equilibrado(n.getDerecha());
    	
    	if(hDer <0){
    		return hDer;
    	}
    	
    	//si no es equilibrado
    	if(Math.abs(hIzq - hDer)>1){
    		return -1;
    	}
    	
    	//si el trozo de arbol es AVL devolvemos la altura
    	return Math.max(hIzq, hDer) + 1;
	}
	
	/**
	 * Obtiene el nodo padre del nodo pasado por parametro. 
	 * En caso de que no tenga padre, devuelve el mismo nodo pasado por parametro.
	 *
	 * @param nodo nodo del que se quiere obtener su nodo padre.
	 * @return nodo padre.
	 */
	public Nodo<T> padre(Nodo<T> nodo){
		Nodo<T> raizTmp = this.getRaiz();
		Stack<Nodo<T>> pila = new Stack<Nodo<T>>();
    	pila.push(raizTmp);	
    	while(raizTmp.getDerecha()!=null || raizTmp.getIzquierda()!=null){
	    	if(this.compararDato(nodo.getDato(), raizTmp.getDato())>0){
	    		if(raizTmp.getDerecha()!=null){   	
	    			raizTmp = raizTmp.getDerecha();
	    		}
	    	}
	    	else if(this.compararDato(nodo.getDato(), raizTmp.getDato())<0){	
	    		if(raizTmp.getIzquierda()!=null){   
		    		raizTmp = raizTmp.getIzquierda();
	    		}
	    	}
	    	if(this.compararDato(nodo.getDato(), raizTmp.getDato())==0){
	    		return pila.pop();
	    	}

	    	pila.push(raizTmp);	
    	}
    	return pila.pop();
	}
	
	/**
	 * Adds all of the elements in the specified collection to this collection (optional operation). The behavior of this operation is undefined if the specified collection is modified while the operation is in progress. (This implies that the behavior of this call is undefined if the specified collection is this collection, and this collection is nonempty.)
	 * This implementation iterates over the specified collection, and adds each object returned by the iterator to this collection, in turn.
	 * Note that this implementation will throw an UnsupportedOperationException unless add is overridden (assuming the specified collection is non-empty). 
	 *
	 * @param c - collection containing elements to be added to this collection.
	 * @throws ClassCastException - if the class of the specified element prevents it from being added to this collection. 
     * @throws NullPointerException - if the specified element is null and this collection does not permit null elements. 
     * @throws IllegalStateException - if the element cannot be added at this time due to insertion restrictions
	 * @return true if this collection changed as a result of the call.
	 */
    public boolean addAll(Collection<? extends T> c) throws ClassCastException, NullPointerException, IllegalStateException{
    	Iterator<? extends T> iter = c.iterator();
    	Iterator<? extends T> iter2 = c.iterator();
    	T dato, primero;
    	boolean insertado = false;
    	
    	//si el arbol no existía
    	if(this.isEmpty()){    		
    		//comprobamos que sean comparables entre si. Sino, salta excepcion.
    		primero = iter.next();
    		while(iter.hasNext()){
    			this.compararDato(primero, iter.next());
    		}
    	}
    	
    	//el arbol ya existía
    	else{
    		//comprobamos que los datos sean comparables con los que ya están dentro del arbol
    		primero = this.getRaiz().getDato();
    		while(iter.hasNext()){
    			this.compararDato(primero, iter.next());
    		}
    	}
    	
    	
    	//solo llega hasta aqui si los elementos son comparables entre si o con los que ya había
    	while(iter2.hasNext()){
    		dato = iter2.next();
    		if(dato!=null){
    			if(this.add(dato)){
    				insertado = true;
    			}
    		}
    	}
    	return  insertado;
    }
    
    /**
     * Removes all of the elements from this collection (optional operation). The collection will be empty after this method returns.
     * This implementation iterates over this collection, removing each element using the Iterator.remove operation. Most implementations will probably choose to override this method for efficiency.
     * Note that this implementation will throw an UnsupportedOperationException if the iterator returned by this collection's iterator method does not implement the remove method and this collection is non-empty. 
     */ 
    public void clear(){
    	Iterator<T> iter = this.iterator();
    	
    	while(iter.hasNext()){
    		remove(iter.next());
    	}
    }
    
    /**
     * Returns true if this collection contains the specified element. More formally, returns true if and only if this collection contains at least one element e such that (o==null ? e==null : o.equals(e)).
     * This implementation iterates over the elements in the collection, checking each element in turn for equality with the specified element. 
     *
     * @param o - element whose presence in this collection is to be tested.
     * @return true if this collection contains the specified element.
     * @throws  ClassCastException - if the type of the specified element is incompatible with this collection (optional).
   	 * @throws NullPointerException - if the specified element is null and this collection does not permit null elements (optional).
     */
    public boolean contains(Object o) throws ClassCastException, NullPointerException{
    	Nodo<T> raizTmp = this.getRaiz();
    	if(this.isEmpty()){
    		return false;
    	}
    	
    	//si es la raiz el buscado
    	if(this.compararDato((T)o, raizTmp.getDato())==0){
	    	return true;
	    }
	    
    	while(raizTmp.getDerecha()!=null || raizTmp.getIzquierda()!=null){

	    	if(this.compararDato((T)o, raizTmp.getDato())>0){
	    		if(raizTmp.getDerecha()!=null){   		
	    			raizTmp = raizTmp.getDerecha();
	    		}else{
	    			return false;
	    		}
	    	}else if(this.compararDato((T)o, raizTmp.getDato())<0){	
	    		if(raizTmp.getIzquierda()!=null){   
		    		raizTmp = raizTmp.getIzquierda();
	    		}else{
	    			return false;
	    		}
	    	}
	    	
	    	if(this.compararDato((T)o, raizTmp.getDato())==0){
	    		return true;
	    	}
    	}
    	return false;
    }
    
    /**
     * Returns true if this collection contains all of the elements in the specified collection.
     * This implementation iterates over the specified collection, checking each element returned by the iterator in turn to see if it's contained in this collection. If all elements are so contained true is returned, otherwise false. 
     *
     * @param c - collection to be checked for containment in this collection.
     * @return true if this collection contains all of the elements in the specified collection.
     * @throws ClassCastException - if the types of one or more elements in the specified collection are incompatible with this collection (optional).
     * @throws NullPointerException - if the specified collection contains one or more null elements and this collection does not permit null elements (optional), or if the specified collection is null.
     */
    public boolean containsAll(Collection<?> c) throws ClassCastException, NullPointerException{   	
    	Iterator<?> iter = c.iterator();
    	List<?> listaArbol = this.inOrden();
    	T dato = null;
    	
    	if(this.isEmpty()){
    		return false;
    	}
    	
    	while(iter.hasNext()){
    		dato = (T)iter.next();
    		
    		if(!listaArbol.contains(dato)){
    			return false;
    		}
    	}
    	return true;
    }
    
    /**
     * Returns true if this collection contains no elements.
     * This implementation returns size() == 0. 
     *
     * @return true if this collection contains no elements.
     */ 
    public boolean isEmpty(){
    	return this.size()==0;
    	//?? tal vez this.getRaiz()==null?
    }
    
    /** 
     * Returns an iterator over the elements contained in this collection. 
     * @return an iterator over the elements contained in this collection.
     */
    public Iterator<T> iterator(){ 
    	List<T> lista= this.inOrden();
    	Iterator<T> iter = lista.iterator();
    	
    	return iter;
    }
    
    /**
     * Removes a single instance of the specified element from this collection, if it is present (optional operation). More formally, removes an element e such that (o==null ? e==null : o.equals(e)), if this collection contains one or more such elements. Returns true if this collection contained the specified element (or equivalently, if this collection changed as a result of the call).
     * This implementation iterates over the collection looking for the specified element. If it finds the element, it removes the element from the collection using the iterator's remove method.
     * Note that this implementation throws an UnsupportedOperationException if the iterator returned by this collection's iterator method does not implement the remove method and this collection contains the specified object. 
     *
     * @param o - element to be removed from this collection, if present.
     * @return true if an element was removed as a result of this call.
     * @throws ClassCastException - if the type of the specified element is incompatible with this collection (optional) .
     * @throws NullPointerException - if the specified element is null and this collection does not permit null elements (optional).
     */
    public boolean remove(Object o) throws ClassCastException, NullPointerException{
    	Nodo<T> borrar=null,mirar=null,cambiar=null, nPadre = null;
    	Nodo<T> raizTmp = this.getRaiz();
    	T c_aux, d_aux;
    	boolean salir = false;
    	int altDer = 0;
    	int altIzq = 0;
    	int a = 0;
    	
    	if(this.isEmpty()){
    		return false;
    	}

    	//el nodo a borrar es la raiz?
    	if(this.compararDato((T)o, raizTmp.getDato())==0){
	    	salir = true;
	    	borrar = raizTmp;
	    }
    	
    	//si no es la raiz, lo buscamos
    	while(!salir && (raizTmp.getDerecha()!=null || raizTmp.getIzquierda()!=null)){

	    	if(this.compararDato((T)o, raizTmp.getDato())>0){
	    		if(raizTmp.getDerecha()!=null){   		
	    			raizTmp = raizTmp.getDerecha();
	    		}else{
	    			return false;
	    		}
	    	}else if(this.compararDato((T)o, raizTmp.getDato())<0){
	    	
	    		if(raizTmp.getIzquierda()!=null){   
		    		raizTmp = raizTmp.getIzquierda();
	    		}else{
	    			return false;
	    		}
	    	}
	    	
	    	if(this.compararDato((T)o, raizTmp.getDato())==0){
	    		salir = true;
	    		borrar = raizTmp;
	    	}
    	}
    

    	//existe el nodo a borrar?
    	if(salir){
    		mirar = borrar;

	    	//es una hoja?
	    	if(borrar.getIzquierda()==null && borrar.getDerecha()==null){
	    		mirar= padre(borrar);
	    		nPadre = padre(borrar);
	    		
	    		//es un arbol raiz con solo un nodo raiz?
	    		if(this.size()==1){
	    			this.raiz = null;
	    		}
	    		
	    		if(nPadre.getIzquierda()!=null && compararDato(nPadre.getIzquierda().getDato(), borrar.getDato())==0){
	    			nPadre.setIzquierda(null);
	    		}else if(nPadre.getDerecha()!=null && compararDato(nPadre.getDerecha().getDato(), borrar.getDato())==0){
	    			nPadre.setDerecha(null);
	    		}
	    		//nos lo cargamos
	    		borrar.setDato(null);
	    	}
	    	
	    	//solo tiene un hijo? (o 2 pero en la misma altura) entonces la altura de ese subarbol será 1 o 2 (altura raiz = 1)
	    	else if(borrar.getAltura()<=2){

	    		if(borrar.getIzquierda()!=null){
	    			borrar.setDato(borrar.getIzquierda().getDato());
	    			borrar.setIzquierda(null);
	    		}
	    		
	    		else if(borrar.getDerecha()!=null){
	    			borrar.setDato(borrar.getDerecha().getDato());
	    			borrar.setDerecha(null);
	    		}
	    	}
	    	
	    	//cuando no es ni un hoja ni su padre. Es decir, está por medio del arbol.
	    	else{

	    		//buscamos el mayor de la izquierda
		    	if(borrar.getIzquierda()!=null){
		    		cambiar = borrar.getIzquierda();
		    		
		    		while(cambiar.getDerecha()!=null){
		    			cambiar = cambiar.getDerecha();
		    		}
		    	}
		    		
		    	//buscamos el menor de la derecha
		    	else if(borrar.getDerecha()!=null){
		    		cambiar = cambiar.getDerecha();
		    	
		    		while(cambiar.getIzquierda()!=null){
		    			cambiar = cambiar.getIzquierda();
		    		}
		    	}
	    	
		    	c_aux = cambiar.getDato();
		    	Nodo<T> papa = padre(cambiar);
		    	
		    	//si el nodo que hemos cambiado se ha quedado con algún hijo...
		    	if(cambiar.getIzquierda()!=null || cambiar.getDerecha()!=null){
			    	if(cambiar.getIzquierda()!=null){
			    		cambiar.setDato(cambiar.getIzquierda().getDato());
			    		cambiar.setIzquierda(null);
			    	}else if(cambiar.getDerecha()!=null){
			    		cambiar.setDato(cambiar.getDerecha().getDato());
			    		cambiar.setDerecha(null);
			    	}
		    	}
		    	//si no tiene hijos ya, lo eliminamos sin más
		    	else{		    	
			    	if(papa.getIzquierda()!=null && compararDato(papa.getIzquierda().getDato(), cambiar.getDato())==0){
			    		papa.setIzquierda(null);
			    	}else{
			    		papa.setDerecha(null);
			    	}
			    	cambiar.setDato(borrar.getDato());
			    	borrar.setDato(c_aux);
		    	}		    
	    	}
	    	
	    	while(equilibrado(this.getRaiz())<0){
    			if(mirar.getDerecha()==null){
	    			altDer = 0;
	    		}else{
	    			altDer = mirar.getDerecha().getAltura();
	    		}
	    		
	    		if(mirar.getIzquierda()==null){
	    			altIzq = 0;
	    		}else{
	    			altIzq = mirar.getIzquierda().getAltura();
	    		}
	    		
    			Nodo<T> cambiar2 = estructurar(mirar, altIzq, altDer);
    			Nodo<T> superior = padre(mirar);
    			
    			//si los nodos modificados tenian un padre anteriormente
    			if(compararDato(superior.getDato(), mirar.getDato())!=0){
    				if(superior.getIzquierda()!=null && compararDato(superior.getIzquierda().getDato(), mirar.getDato())==0){
	    				superior.setIzquierda(cambiar2);		
		    		}
		    		else if(superior.getDerecha()!=null && compararDato(superior.getDerecha().getDato(), mirar.getDato())==0){
	    				superior.setDerecha(cambiar2);
	    			}
    			}else{
    				this.raiz = cambiar2;
    			}
    			mirar = padre(mirar);
    		}
    		return true;	    	
    	}	
    	return false;
    }
    
   	
    /**
     * Removes all of this collection's elements that are also contained in the specified collection (optional operation). After this call returns, this collection will contain no elements in common with the specified collection.
     * This implementation iterates over this collection, checking each element returned by the iterator in turn to see if it's contained in the specified collection. If it's so contained, it's removed from this collection with the iterator's remove method.
     * Note that this implementation will throw an UnsupportedOperationException if the iterator returned by the iterator method does not implement the remove method and this collection contains one or more elements in common with the specified collection. 
     *
     * @param c - collection containing elements to be removed from this collection.
     * @return c - collection containing elements to be removed from this collection.
     * @throws ClassCastException - if the types of one or more elements in this collection are incompatible with the specified collection (optional) .
     * @throws NullPointerException - if this collection contains one or more null elements and the specified collection does not support null elements (optional), or if the specified collection is null.
     */ 
    public boolean removeAll(Collection<?> c) throws ClassCastException, NullPointerException{
    	T dato;
    	boolean noBorrado = false;
    	
    	Iterator<?> iter = c.iterator();
    	while(iter.hasNext()){
    		dato = (T)iter.next();
    		    		
    		if(remove(dato)){
    			noBorrado = true;
    		}
    	}
    		
    	return noBorrado;
    }
    
    /**
     * Retains only the elements in this collection that are contained in the specified collection (optional operation). In other words, removes from this collection all of its elements that are not contained in the specified collection.
     * This implementation iterates over this collection, checking each element returned by the iterator in turn to see if it's contained in the specified collection. If it's not so contained, it's removed from this collection with the iterator's remove method.
     * Note that this implementation will throw an UnsupportedOperationException if the iterator returned by the iterator method does not implement the remove method and this collection contains one or more elements not present in the specified collection. 
     *
     * @param c - collection containing elements to be retained in this collection.
     * @return true if this collection changed as a result of the call .
     * @throws ClassCastException - if the types of one or more elements in this collection are incompatible with the specified collection (optional) .
     * @throws NullPointerException - if this collection contains one or more null elements and the specified collection does not permit null elements (optional), or if the specified collection is null.
     */
    public boolean retainAll(Collection<?> c) throws ClassCastException, NullPointerException{
    	List<T> listaArbol = this.preOrden();
    	List<T> listaBorrar = new ArrayList<T>();
    	T dato;
    	boolean modificada = false;
    	
    	for(int i=0; i<listaArbol.size(); i++){
      		if(!c.contains(listaArbol.get(i))){
    			listaBorrar.add(listaArbol.get(i));
    		}
    	}
    	
		Iterator<?> iter = listaBorrar.iterator();
    	while(iter.hasNext()){
    		modificada = true;
    		dato = (T)iter.next();
    		    		
    		remove(dato);
    	}
    	
    	return modificada;
    }
    
    /**
     * Returns the number of elements in this collection. If this collection contains more than Integer.MAX_VALUE elements, returns Integer.MAX_VALUE. 
     * @return the number of elements in this collection.
     */
    public int size(){
    	return this.preOrden().size();
    }

    /**
     * Este método recorre el árbol mediante el recorrido INORDEN y almacena cada elemento.
     * en una lista que se devuelve al terminar el recorrido.
     * @return lista Lista en inorden con el contenido del arbol.
     */
     
    public List<T> inOrden(){
		List<T> lista = new ArrayList<T>();
    	Nodo<T> nodo = this.getRaiz();  	
    	Stack<Nodo<T>> pila = new Stack<Nodo<T>>();
     	
     	while((nodo!=null &&nodo.getDato()!=null)|| !pila.empty()){
     		if(nodo!=null){
     			pila.push(nodo);
     			nodo = nodo.getIzquierda();
     		}else{
     			nodo = pila.pop();
     			lista.add(nodo.getDato());
     			nodo = nodo.getDerecha();
     		}
     	} 	
    	
    	return lista;
    }
    
    
    /**
     * Este método recorre el árbol mediante el recorrido PREORDEN y almacena cada
     * elemento en una lista que se devuelve al terminar el recorrido.
     * @return lista Lista en preorden con el contenido del arbol.
     */
    public List<T> preOrden(){
    	List<T> lista = new ArrayList<T>();
    	Nodo<T> nodo = this.getRaiz();  	
    	Stack<Nodo<T>> pila = new Stack<Nodo<T>>();

     	while((nodo!=null && nodo.getDato()!=null) || !pila.empty()){
     		if(nodo!=null){
     			lista.add(nodo.getDato());
     			pila.push(nodo);
     			nodo = nodo.getIzquierda();
     		}else{
     			nodo = pila.pop();
     			nodo = nodo.getDerecha();
     		}
     	} 	
    	
    	return lista;
    }
    

    /**
     * Este método recorre el árbol mediante el recorrido POSTORDEN y almacena cada
     * elemento en una lista que se devuelve al terminar el recorrido.
     * @return lista Lista en postOrden con el contenido del arbol.
     */
    public List<T> postOrden(){
    	List<T> lista = new ArrayList<T>();
    	Nodo<T> nodo = this.getRaiz();  	
    	Stack<Nodo<T>> pila1 = new Stack<Nodo<T>>();
    	Stack<Boolean> pila2 = new Stack<Boolean>();
    	
    	while((nodo!=null && nodo.getDato()!=null) || !pila1.empty()){
    		
    		if(nodo!=null){
    			pila1.push(nodo);
    			pila2.push(true);
    			nodo = nodo.getIzquierda();
    		}else{
    			nodo = pila1.pop();
    			if(pila2.pop()){
    				pila1.push(nodo);
    				pila2.push(false);
    				nodo = nodo.getDerecha();
    			}else{
    				lista.add(nodo.getDato());
    				nodo = null;
    			}
    		}
    	}
    	
    	return lista;
    }
    
    /**
     * Se devuelve la altura del Nodo que contiene al dato pasado por parámetro, si 
     * el dato nose encuentra en el árbol se devuelve -1.
     * @param dato dato del que buscamos la altura.
     * @return altura del nodo
     */
    public int altura(T dato){
    	Nodo<T> nodo = this.getNodo(dato);
    	if(!this.contains(dato)){
    		return -1;
    	}
    	
    	return nodo.getAltura();
    }

    /**
     * Se devuelve la profundidad del Nodo que contiene al dato pasado por parámetro,
     * si eldato no se encuentra en el árbol se devuelve -1. La profundidad del nodo
     * raíz es 0. 
     * @param dato dato del que buscamos su profundidad.
     * @return profundidad del dato pasado por parametro
     */
    public int profundidad(T dato){
    	Nodo<T> nodo = new Nodo<T>(dato);
    	int profundidad = 0;
    	while(compararDato(nodo.getDato(), this.getRaiz().getDato())!=0){
    		profundidad++;
    		nodo = padre(nodo);
    	}
    	
    	return profundidad;
    }
    
    /**
     * Devuelve el Nodo que es la raíz del árbol.
     * @return nodo raiz.
     */
    public Nodo<T> getRaiz(){
    	return this.raiz;
    }
    
    /**
     * Devuelve el Nodo que contiene al dato pasado por parámetro.
     * @param dato dato del nodo a buscar.
     * @return nodo con el dato a buscar.
     */
    public Nodo<T> getNodo(T dato){
     	Nodo<T> raizTmp = this.getRaiz();
     	
     	if(this.isEmpty()){
     		return null;
     	}
    	
   		while(raizTmp.getDerecha()!=null || raizTmp.getIzquierda()!=null){

	    	if(this.compararDato(dato, raizTmp.getDato())>0){
	    		if(raizTmp.getDerecha()!=null){   		
	    			raizTmp = raizTmp.getDerecha();
	    		}else{
	    			return null;
	    		}
	    	}else if(this.compararDato(dato, raizTmp.getDato())<0){	
	    		if(raizTmp.getIzquierda()!=null){   
		    		raizTmp = raizTmp.getIzquierda();
	    		}else{
	    			return null;
	    		}
	    	}
	    	
	    	if(this.compararDato(dato, raizTmp.getDato())==0){
	    		return raizTmp;
	    	}
    	}
    	
    	return raizTmp;
    }
    
    /**
     * Devuelve el comparator que utiliza el árbol.
     * @return comparador que utiliza el arbol.
     */
    private Comparator<T> getComparator(){
    	return this.comparador;
    }
    
    /**
     * Extrae el dato de un nodo.
     * @param nodo nodo del que buscamos extraer el el nodo.
     * @return dato
     */
    public T extraeDato(Nodo<T> nodo){
    	return nodo.getDato();
    }
    
    /**
     * Funcion que compara mediante un modo u otro dependiendo de si el objeto 
     * que se pasa por parametro es comparable o necesita de una funcion especifica.
     * @param t1 dato1
     * @param t2 dato2
     * @return un int negativo, un cero, o un int positivo si el primer objeto es 
     * menor, igual o mayor respectivametne con especto al segundo objeto pasado por parametro.
     */
    private int compararDato(T t1, T t2){
    	if(this.comparador==null){
    		return ((Comparable)t1).compareTo(t2);
    	}else{
    		return this.comparador.compare(t1,t2);
    	}
    }

	/******************************************************************/
	/******************************************************************/
	
	/**
	 * Implementa los metodos pertenecientes a la estructura de un nodo.
	 */
	private class Nodo<T>{
		/**
		 * Dato contenido en el nodo.
		 */
		private T dato;
		
		/**
		 * Nodo hijo izquierda.
		 */
		private Nodo<T> izquierda;
		
		/**
		 * Nodo hijo derecha.
		 */
		private Nodo<T> derecha;
		
		/**
		 * Factor de equilibrio del nodo.
		 */
		private int factorE;
	
		/**
		 * constructor por defecto.
		 */
		public Nodo(){	
			dato = null;
			izquierda = null;
			derecha = null;
			factorE = 0;
		}    
	
		/**
		 * Constructor con un parametro.
		 * @param dato que contendra el nodo.
		 */
		public Nodo(T dato){	
			this.dato = dato;
			izquierda = null;
			derecha = null;
			factorE = 0;
		}    
		
		/**
		 * Devuelve el nodo a la izquierda del actual.
		 * @return nodo hijo izquierdo.
		 */
		public Nodo<T> getIzquierda(){
			return izquierda;
		}
		/**
		 * Devuelve el nodo a la derecha del actual.
		 * @return nodo hijo derecho.
		 */
		public Nodo<T> getDerecha(){
			return derecha;
		}
		
		/**
		 * Devuelve el dato contenido en el nodo.
		 * @return dato contenido en el nodo.
		 */
		public T getDato(){
			return dato;
		}
		
		/**
		 * Asigna un nodo al hijo derecho del nodo.
		 * @param derecha dato a insertar.
		 */
		public void setDerecha(Nodo<T> derecha){
			this.derecha = derecha;
		}
		
		/**
		 * Asigna un nodo al hijo izquierdo del nodo.
		 * @param izquierda dato a insertar.
		 */
		public void setIzquierda(Nodo<T> izquierda){
			this.izquierda = izquierda;
		}
		
		/**
		 * Asigna un dato al nodo.
		 * @param dato dato a insertar en el nodo.
		 */
		public void setDato(T dato){
			this.dato = dato;
		}
		
		/**
		 * Obtiene el factor de equilibrio del nodo.
		 * @return factor de equilibrio del nodo.
		 */
		public int getFactorE(){
			int altDer = 0;
			int altIzq = 0;
			if(this.getDerecha()!=null){
		    	altDer = this.getDerecha().getAltura();
		   	}
		   	if(this.getIzquierda()!=null){		    
		   		altIzq = this.getIzquierda().getAltura();
		   	}
			return (altDer - altIzq);
		}
		
		/**
		 * Asigna un valor al factor de equilibrio.
		 * @param fe factor de equilibro para asignar al nodo.
		 */
		public void setFactorE(int fe){
			this.factorE = fe;
		}
		
		/**
		 * Devuelve la altura del nodo.
		 * @return altura del nodo.
		 */	
		public int getAltura(){
			int hIzq = 0;
			int hDer = 0;
			
			if(this.getDato()==null){
			  return 0;
	    	}
	
	
			if(this.getIzquierda()!=null){	
				hIzq = this.getIzquierda().getAltura();
			}else{
				hIzq = 0;
			}
	    	
	    	if(this.getDerecha()!=null){   
	    		hDer = this.getDerecha().getAltura();
	    	}else{
	    		hDer = 0;
	    	}
	    	return Math.max(hIzq, hDer) + 1;
		}
	}
}

