# ASMSignatures
ASMSignatures provides objects for class signatures, method signatures, and type signatures. Class signatures are represented by the `ClassSignature` class, method signatures are represented by the `MethodSignature` class, and type signatures are represented by the `TypeInformal` class. ASMSignatures (as the name suggests) uses ASM's `SignatureReader` and `SignatureVisitor`s to build the objects. The entry point into ASMSignatures is located in the [`Signature`](https://github.com/PlutoPowered/ASMSignatures/blob/master/src/main/java/com/gmail/socraticphoenix/asmsig/Signatures.java) class.

Using the library:
- [JavaDocs](https://socraticphoenix.github.io/ASMSignatures/)
 - Example build.gradle:

```groovy
group 'example'
version '0.0.0'

apply plugin: 'java'
sourceCompatibility = 1.8

repositories {
    maven {
        name "jitpack.io"
        url "https://jitpack.io"
    }
}

dependencies {
    compile "com.github.PlutoPowered:ASMSignatures:master-SNAPSHOT"
}

```
