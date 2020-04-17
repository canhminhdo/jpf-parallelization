package abp.main;

public class Cell<E> {
    private E element;
    public Cell(E e) { this.element = e; }
    public E get() { return element; }
    public void set(E e) { this.element = e; }
}