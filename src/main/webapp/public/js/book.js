/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

// This is a factory function that returns a new range object.function 
function range(from, to) {    // Use Object.create() to create an object that inherits from the    

    let r = Object.create(range.methods);

    r.from = from;
    r.to = to;
    return r;
}
range.methods = {
    includes(x) {
        return this.from <= x && x <= this.to;
    },
    * [Symbol.iterator] () {
        for (let x = Math.ceil(this.from); x <= this.to; x++)
            yield x;
    },
    toString() {
        return "(" + this.from + "..." + this.to + ")";
    }
};
Number.prototype.times = function (f, context) {
    let n = this.valueOf();
   // console.log(n);
    for (var i = 0; i < n; i++) {
       f.call(context,i);
       
    }
};
$(document).ready(function () {

    let n = 3;
    n.times((i) => {   alert("hello"); });
    let r = range(1, 3);

    console.log(r.includes(2));
    console.log(r.toString());
    console.log([...r]);

});