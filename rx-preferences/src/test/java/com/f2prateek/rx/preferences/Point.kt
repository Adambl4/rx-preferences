package com.f2prateek.rx.preferences

internal class Point(val x: Int, val y: Int) {

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o !is Point) return false
        return x == o.x && y == o.y
    }

    override fun hashCode(): Int {
        return 31 * x + y
    }

    override fun toString(): String {
        return "Point{x=$x, y=$y}"
    }
}
