package devcon.core

class ObservableData<T>(
    initialValue: T,
) {
    private val observers = mutableListOf<(T) -> Unit>()
    var value: T = initialValue
        private set

    fun observe(observer: (T) -> Unit) {
        observers.add(observer)
        observer(value)
    }

    fun setValue(newValue: T) {
        value = newValue
        observers.forEach { it(newValue) }
    }
}
