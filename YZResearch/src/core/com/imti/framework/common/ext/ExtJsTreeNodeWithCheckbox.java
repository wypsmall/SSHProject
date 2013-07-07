package com.imti.framework.common.ext;

import java.io.Serializable;

public class ExtJsTreeNodeWithCheckbox extends ExtJsTreeNode implements
		Serializable {
	private boolean checked;

	public ExtJsTreeNodeWithCheckbox() {
		this.checked = false;
	}

	public boolean isChecked() {
		return this.checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
}
