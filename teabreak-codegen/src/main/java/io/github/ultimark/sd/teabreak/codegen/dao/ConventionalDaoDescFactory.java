package io.github.ultimark.sd.teabreak.codegen.dao;

import org.seasar.doma.gradle.codegen.desc.DaoDesc;
import org.seasar.doma.gradle.codegen.desc.DaoDescFactory;
import org.seasar.doma.gradle.codegen.desc.EntityDesc;

public class ConventionalDaoDescFactory extends DaoDescFactory {

	public ConventionalDaoDescFactory(String packageName, String suffix, String configClassName) {

		super(packageName, suffix, configClassName);
	}
	
	@Override
	public DaoDesc createDaoDesc(EntityDesc entityDesc) {

		return resolveDaoName(entityDesc, super.createDaoDesc(entityDesc));
	}
	
	private DaoDesc resolveDaoName(EntityDesc entityDesc, DaoDesc daoDesc) {
		
		daoDesc.setSimpleName(entityDesc.getSimpleName().concat("Dao"));
		
		return daoDesc;
	}
}