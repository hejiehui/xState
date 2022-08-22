package com.xrosstools.xstate.idea.editor.model;

public enum RouteStyle {
	direct, heightFirst, widthFirst;
	
	public static String[] getNames() {
		return new String[]{direct.name(), heightFirst.name(), widthFirst.name()};
	}
	
	public static Integer getIndex(RouteStyle style) {
		return style == direct ? 0 : style == heightFirst ? 1 : 2;
	}
}
