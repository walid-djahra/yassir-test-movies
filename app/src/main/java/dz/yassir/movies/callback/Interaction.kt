package dz.yassir.movies.callback

/**
 * @author Djahra walid , created on 12/03/2023
 */
interface Interaction<T> {
    /**
     * To pass clicked item from adapter to fragment
     */
    fun onInteraction(item : T, position : Int)
}