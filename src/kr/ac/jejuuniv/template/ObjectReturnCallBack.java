package kr.ac.jejuuniv.library.template;

import java.io.BufferedReader;

public interface ObjectReturnCallBack<T> {

	T execute(BufferedReader bufferedReader) throws Exception;

}
