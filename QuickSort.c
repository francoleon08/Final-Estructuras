//Metodo QuickSort, mejor opcion ya que o(n log(n))
void ordenarQuickSort(int array[], int n) {
    QuickSort(array, 0, n);
}

void QuickSort(int array[], int primero, int ultimo)
{
    int i, j, pivote, t;
    i = primero;
    j = ultimo;
    pivote = array[(primero+ultimo)/2];
    do {
        //Busco el menor del lado izquierdo
        while (array[i]<pivote)
            i++;
        //Busco el major del lado derecho
        while (pivote<array[j])
            j--;
        //Intercambia i con j
        if (i<=j) {
            intercambiar(array, i ,j);
            i++;
            j--;
        }
    } while (i<=j);
    //Llamado recursivo con la parte izquierda del pivote
    if (primero<j)
        QuickSort(array,primero,j);
    //Llamado recursivo con la parte derecha del pivote
    if (i<ultimo)
        QuickSort(array,i,ultimo);
}
