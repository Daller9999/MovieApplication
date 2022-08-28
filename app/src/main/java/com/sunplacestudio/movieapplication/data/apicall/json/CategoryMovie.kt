package com.sunplacestudio.movieapplication.data.apicall.json

enum class CategoryMovie {
    RECOMMENDED,
    POPULAR,
    FOUND,
    NONE;

    companion object {
        fun getCategory(pos: Int): CategoryMovie {
            for (value in values()) {
                if (value.ordinal == pos)
                    return value
            }
            return NONE
        }
    }
}