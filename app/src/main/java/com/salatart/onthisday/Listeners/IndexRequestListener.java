package com.salatart.onthisday.Listeners;

/**
 * Created by sasalatart on 10/23/16.
 */

public interface IndexRequestListener<T> {
    public void OnSuccess(T[] array);
    public void OnFailure(String message);
}
