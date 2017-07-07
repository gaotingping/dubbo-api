var api_m = {
	"id" : {
		"desc" : "主键",
		"type" : "int",
		"fields" : null
	},
	"address" : {
		"desc" : "地址",
		"type" : "com.gtp.dubbo.test.vo.AddressVO",
		"fields" : {
			"id" : {
				"desc" : "主键",
				"type" : "int",
				"fields" : null
			},
			"name" : {
				"desc" : "地址全名",
				"type" : "java.lang.String",
				"fields" : null
			},
			"address2" : {
				"desc" : "地址2",
				"type" : "com.gtp.dubbo.test.vo.AddressVO2",
				"fields" : {
					"id" : {
						"desc" : "主键",
						"type" : "int",
						"fields" : null
					},
					"name" : {
						"desc" : "地址全名",
						"type" : "java.lang.String",
						"fields" : null
					},
					"address3" : {
						"desc" : "地址3",
						"type" : "com.gtp.dubbo.test.vo.AddressVO3",
						"fields" : {
							"id" : {
								"desc" : "主键",
								"type" : "int",
								"fields" : null
							},
							"name" : {
								"desc" : "地址全名",
								"type" : "java.lang.String",
								"fields" : null
							},
							"address2" : {
								"desc" : "引用关联2",
								"type" : "com.gtp.dubbo.test.vo.AddressVO2",
								"fields" : "$ref_com.gtp.dubbo.test.vo.AddressVO2"
							},
							"baseVO" : {
								"desc" : "baseVO",
								"type" : "com.gtp.dubbo.test.vo.BaseVO",
								"fields" : {
									"pid" : {
										"desc" : "我是公共父类的属性pid",
										"type" : "int",
										"fields" : null
									}
								}
							},
							"user" : {
								"desc" : "引用关联1",
								"type" : "com.gtp.dubbo.test.vo.UserInfoVO",
								"fields" : "$ref_com.gtp.dubbo.test.vo.UserInfoVO"
							}
						}
					},
					"baseVO" : {
						"desc" : "baseVO",
						"type" : "com.gtp.dubbo.test.vo.BaseVO",
						"fields" : {
							"pid" : {
								"desc" : "我是公共父类的属性pid",
								"type" : "int",
								"fields" : null
							}
						}
					}
				}
			}
		}
	},
	"name" : {
		"desc" : "用户名",
		"type" : "java.lang.String",
		"fields" : null
	},
	"pid" : {
		"desc" : "我是公共父类的属性pid",
		"type" : "int",
		"fields" : null
	},
	"list" : {
		"desc" : "多个地址",
		"type" : "java.util.List",
		"fields" : [ {
			"id" : {
				"desc" : "主键",
				"type" : "int",
				"fields" : null
			},
			"name" : {
				"desc" : "地址全名",
				"type" : "java.lang.String",
				"fields" : null
			},
			"address2" : {
				"desc" : "地址2",
				"type" : "com.gtp.dubbo.test.vo.AddressVO2",
				"fields" : {
					"id" : {
						"desc" : "主键",
						"type" : "int",
						"fields" : null
					},
					"name" : {
						"desc" : "地址全名",
						"type" : "java.lang.String",
						"fields" : null
					},
					"address3" : {
						"desc" : "地址3",
						"type" : "com.gtp.dubbo.test.vo.AddressVO3",
						"fields" : {
							"id" : {
								"desc" : "主键",
								"type" : "int",
								"fields" : null
							},
							"name" : {
								"desc" : "地址全名",
								"type" : "java.lang.String",
								"fields" : null
							},
							"address2" : {
								"desc" : "引用关联2",
								"type" : "com.gtp.dubbo.test.vo.AddressVO2",
								"fields" : "$ref_com.gtp.dubbo.test.vo.AddressVO2"
							},
							"baseVO" : {
								"desc" : "baseVO",
								"type" : "com.gtp.dubbo.test.vo.BaseVO",
								"fields" : {
									"pid" : {
										"desc" : "我是公共父类的属性pid",
										"type" : "int",
										"fields" : null
									}
								}
							},
							"user" : {
								"desc" : "引用关联1",
								"type" : "com.gtp.dubbo.test.vo.UserInfoVO",
								"fields" : "$ref_com.gtp.dubbo.test.vo.UserInfoVO"
							}
						}
					},
					"baseVO" : {
						"desc" : "baseVO",
						"type" : "com.gtp.dubbo.test.vo.BaseVO",
						"fields" : {
							"pid" : {
								"desc" : "我是公共父类的属性pid",
								"type" : "int",
								"fields" : null
							}
						}
					}
				}
			}
		} ]
	},
	"user" : {
		"desc" : "用户信息自关联",
		"type" : "com.gtp.dubbo.test.vo.UserInfoVO",
		"fields" : "$ref_com.gtp.dubbo.test.vo.UserInfoVO"
	}
}