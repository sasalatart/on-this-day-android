package com.salatart.onthisday.Listeners;

import java.util.ArrayList;

/**
 * Created by sasalatart on 10/23/16.
 */

public interface IndexRequestListener<T> {
    public void OnSuccess(ArrayList<T> array);

    public void OnFailure(String message);
}
