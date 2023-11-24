package com.example.math.models

import kotlin.random.Random

class Question private constructor(
    var var1: Int,
    var var2: Int,
    var result: Int,
    var symbol: Int
) {

    companion object {
        fun generate(level: Int): Question {
            var limit = 11
            if (level == 1) limit = 21
            if (level == 2) limit = 51
            var v1 = Random.nextInt(1, limit)
            var v2 = Random.nextInt(1, limit)
            val symbol = Random.nextInt(4)

            if (v1 == 2 && v2 == 2) v1 = Random.nextInt(3, limit)

            if (symbol > 1){
                if (v1 == 1) v1 = Random.nextInt(3, limit)
                if (v2 == 1) v2 = Random.nextInt(2, limit)
            }

            if (level == 0 && symbol == 0){
                if (v1 < v2){
                    val k = v1
                    v1 = v2
                    v2 = k
                }
            }

            var res = when (symbol) {
                0 -> v1 - v2
                1 -> v1 + v2
                else -> v1 * v2
            }
            if (symbol == 2) {
                val k = v1
                v1 = res
                res = k
            }
            return Question(v1, v2, res, symbol)
        }
    }
}