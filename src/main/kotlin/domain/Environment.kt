package domain

class Environment {

    private val global = mutableMapOf<String, Any>()
    private val closures = ArrayDeque<Closure>()

    fun find(name: String): Any {
        closures.forEach {
            var closure: Closure? = it
            while(closure != null) {
               if(name in closure.env) {
                   return closure.env[name]!!
               }
                closure = closure.parent
            }
        }
        return global[name]!!
    }

    fun exists(name: String): Boolean {
        closures.forEach {
            var closure: Closure? = it
            while(closure != null) {
                if(name in closure.env) {
                    return true
                }
                closure = closure.parent
            }
        }
        return global[name] != null
    }

    fun add(key: String, value: Any) {
        if(closures.isEmpty()) {
            global[key] = value
        } else {
            closures[0].env[key] = value
        }
    }

    fun set(key: String, value: Any) {
        closures.forEach {
            var closure: Closure? = it
            while(closure != null) {
                if(key in closure.env) {
                    closure.env[key] = value
                }
                closure = closure.parent
            }
        }
        global[key] = value
    }

    fun getFirstClosure(): Closure? {
        return if(closures.isEmpty()) {
            null
        } else {
            closures[0]
        }
    }

    fun addFirstClosure(closure: Closure) = closures.addFirst(closure)
    fun setFirstClosure(closure: Closure) { closures[0] = closure }
    fun removeFirstClosure() = closures.removeFirst()
    fun clear() {
        global.clear()
        closures.clear()
    }
}
