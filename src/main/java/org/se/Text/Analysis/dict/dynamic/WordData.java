package org.se.Text.Analysis.dict.dynamic;

/**
 * @author Val Richter
 */
public interface WordData {
	public WordData fromStr(String s);

	public String getStr();

	public Object getVal();
}
