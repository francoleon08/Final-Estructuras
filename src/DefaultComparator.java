public class DefaultComparator<E> implements java.util.Comparator<E> {
    public int compare( E a, E b ) throws ClassCastException {
        return ((Comparable<E>)a).compareTo(b);
    }
}