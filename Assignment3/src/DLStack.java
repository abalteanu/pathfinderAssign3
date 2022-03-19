
public class DLStack<T> implements DLStackADT<T> {
	
	DoubleLinkedNode<T> top = new DoubleLinkedNode<T>();
	
	int numItems;
	
	/**
	 * Constructor that creates an empty stack. Adjust instance variables accordingly (making top = null and numItems = 0)
	 */
	public DLStack() {
		top = null;
		numItems = 0;
	}
	
	/**
	 * Adds the given dataItem to top of stack
	 */
	public void push(T dataItem) {
		DoubleLinkedNode<T> newNode = new DoubleLinkedNode<T>(dataItem);
		
		if(top == null) {
			// if the list was empty, set the top to newNode
			top = newNode;
		} else {
			// linking newNode to the list
			top.setNext(newNode);
			newNode.setPrevious(top);
			
			// assigning newNode to top
			top = newNode;
			
		}

		// increment numItems by 1
		numItems++;
	}

	 /**
	  * Removes and returns the top data item of the stack, returning its item
	  * @return item, the element stored in the popped node
	  */
	
	public T pop() throws EmptyStackException{
		
		// if the list is empty throw an exception
		if(numItems == 0) throw new EmptyStackException("Stack is Empty");
		
		T item = top.getElement();
		
		if(numItems == 1) {
			// if there is only one item, popping this item empties the list
			top = null;
		}
		else {
			// if the list has more than one item,
			top = top.getPrevious(); // set top to previous item
			top.setNext(null);	// making the top the end of the list
		}
		
		// decrementing number of items
		numItems--;
		return item;
		
	}
	
	
	 /**
	  * Removes and returns the kth data item of the stack, returning its item
	  * @return item, the element stored in the popped node
	  */
	public T pop(int k) throws InvalidItemException{
		// k = 1 means remove the item from the top of the stack
		if(k <= 0 || k > numItems) {
			throw new InvalidItemException("Invalid index to remove from stack");
		}
		
		T item = top.getElement(); // item to be returned starts as the element held in the top node, unless changed further in the program

		// if k is the number of items, just pop the top item of the stack (will return item containing the element in the top node)
		if(k == 1) item = this.pop();
		else {
			
			DoubleLinkedNode<T> curr = top;	// current node for iterating throug

			for(int i = 0; i<k; i++) {
				// iterating through the list until the node to remove, starting from top and going to the previous node for the length
				curr = curr.getPrevious();
			}
			
			// setting the item value to the element in the node to remove
			item = curr.getElement();

			if(k==numItems) {
				curr = curr.getNext();	// backtracking to get second last node 
				// if the last item on the list is the node to remove, set the second last node to null, making it the last node
				curr.setPrevious(null);
			} else {
				DoubleLinkedNode<T> prev = curr.getPrevious();
				curr = curr.getNext();
				// if the node to pop is in the middle of the list, remove the node by adjusting the two nodes adjacent to it so that the
				// two adjacent nodes point to each other instead of to the node to remove. 
				curr.setPrevious(prev);
				prev.setNext(curr);
			}
		}
		
		numItems--;
		return item;
		
	}
	
	/**
	 * returns the element at the top of the stack without removing it
	 * @return element at top
	 */
	public T peek() throws EmptyStackException{
		return top.getElement();
	}
	
	/**
	 * Returns true if the stack is empty
	 * @return true or false if the stack is empty or not
	 */
	public boolean isEmpty() {
		if(numItems == 0) return true;
		else return false;
	}
	
	/**
	 * returns the number of data items in the stack
	 * @return numItems
	 */
	public int size() {
		return numItems;
	}
	
	/** 
	 * returns top element of the list
	 * @return DoubleLinkedNode top
	 */
	public DoubleLinkedNode<T> getTop(){
		return top;
	}
	
	public String toString() {
		String data = "[";
		String item;
		
		DoubleLinkedNode<T> curr = top;

		
		for(int i = 0; i < numItems; i++) {
			
			item = (String)curr.getElement();
			
			if(i == numItems-1) {
				// if on last item of list, do not add a space
				data += item;
			} else {
				data = item + " ";
			}
			
			data += "]";
			
			//going to next data item
			curr = curr.getPrevious();
		}
		
		return data;
	}
}
