import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * 
 * @author Kamil
 * @version 1.0
 * @param <T>
 */

public class Quicksort<T extends Comparable<T>> extends RecursiveAction{

	private T[] tab;
	int left;
	int right;
	
	
	/**
	 * Constructor
	 * @param tab array of variables to sort
	 * @param l left edge index of array
	 * @param r right edge index of array
	 */
	public Quicksort(T[] tab, int l, int r)
	{
		this.tab = tab;
		left = l;
		right = r;
	}
	
	/**
	 * Classic quicksort method, without concurrency
	 */
	public void quicksort()
	{
		quick(0,tab.length-1);
	}
	
	private void quick(int l, int p)
	{
		int q;
		if(l<p)
		{
			q = partition(l,p);
			quick(l,q);
			quick(q+1,p);
		}
	}
	
	/**
	 * 
	 * @param l left edge of subarray
	 * @param p right edge of subarray
	 * @return index of pivot
	 */
	public int partition(int l, int p)
	{
		T x = tab[l];
		int i = l; int j=p;
		while(true)
		{
			while(tab[j].compareTo(x)>0)
				j--;
			while(tab[i].compareTo(x)<0)
				i++;
			if(i<j)
			{
				T w = tab[i];
				tab[i] = tab[j];
				tab[j] = w;
				i++;j--;
			}
			else
				return j;
		}
	}
	
	/**
	 * Override method RecursiveAction. Use Fork/Join framework and sort array with concurrency
	 */
	@Override
	protected void compute() {
		
		if(left<right)
		{
			int pivot = partition(left,right);
			invokeAll(new Quicksort<>(tab, left, pivot), new Quicksort<>(tab,pivot+1,right));
		}
		
	}
	
}
