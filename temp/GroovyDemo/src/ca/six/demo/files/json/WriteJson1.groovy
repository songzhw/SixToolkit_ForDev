package ca.six.demo.files.json

import groovy.json.JsonBuilder

JsonBuilder builder = new JsonBuilder()
builder.customer {
    name "John"
    address {
        province "ON"
        country "Canada"
    }
}
println builder.toPrettyString()

