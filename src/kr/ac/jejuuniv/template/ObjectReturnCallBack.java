package kr.ac.jejuuniv.template;

import java.io.BufferedReader;

public interface ObjectReturnCallBack<T> {

	T execute(BufferedReader bufferedReader) throws Exception;

}
