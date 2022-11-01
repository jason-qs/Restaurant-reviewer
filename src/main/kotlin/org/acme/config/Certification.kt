package org.acme.config

import java.security.KeyPairGenerator
import java.util.*

class Certification {
    fun generateKeyPairs() {
        val keyGenerator = KeyPairGenerator.getInstance("RSA")
        keyGenerator.initialize(4096)
        val keys = keyGenerator.generateKeyPair()
        println(Base64.getMimeEncoder().encodeToString(keys.private.encoded))
        println(Base64.getMimeEncoder().encodeToString(keys.public.encoded))
    }
}