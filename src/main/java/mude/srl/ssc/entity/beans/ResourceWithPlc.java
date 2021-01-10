package mude.srl.ssc.entity.beans;

import com.fasterxml.jackson.annotation.JsonProperty;


import mude.srl.ssc.entity.Resource;

public class ResourceWithPlc {

	 private Long id;
	 
	 @JsonProperty(value = "text")
	 private String tag;
	 
	 private Short busId;
	 
	 
	 private  String plc_uid;
	 
	 private  Short plc_id;
	 
	 private String  iconCls = "icon-cabina";
	 
	 
	 private String  nodeType = "resource";
	 
	 
	 
	public ResourceWithPlc(Resource r) {
		this.id = r.getId();
		this.tag = r.getTag();
		this.busId = r.getBusId();
		if(r.getPlc()!=null) {
			this.plc_id = r.getPlc().getId();
			this.plc_uid =r.getPlc().getUid(); 
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public Short getBusId() {
		return busId;
	}

	public void setBusId(Short busId) {
		this.busId = busId;
	}

	public String getPlc_uid() {
		return plc_uid;
	}

	public void setPlc_uid(String plc_uid) {
		this.plc_uid = plc_uid;
	}

	public Short getPlc_id() {
		return plc_id;
	}

	public void setPlc_id(Short plc_id) {
		this.plc_id = plc_id;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}
	
	
}
