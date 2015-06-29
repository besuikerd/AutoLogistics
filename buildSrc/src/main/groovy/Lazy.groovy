class LazyValue extends Closure{
    Closure closure
    def value

    LazyValue(closure) {
        super(null)
        this.closure = closure
    }

    @Override
    def call(){
        if(value == null){
            value = closure()
        }
        return value
    }
}
