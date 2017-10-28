package org.apache.catalina;

import java.lang.instrument.ClassFileTransformer;

public interface InstrumentableClassLoader {
	void addTransformer(ClassFileTransformer transformer);
	void removeTransformer(ClassFileTransformer transformer);
	ClassLoader copyWithoutTransformers();
}
