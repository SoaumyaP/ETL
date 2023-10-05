package com.csfe.common.business.dao.delivery;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.csfe.common.CSFEException;
import com.csfe.common.base.BaseDAO;
import com.csfe.common.business.entity.DeliveryRequest;

@Transactional
@Repository("DeliveryRequestDAO")
public class DeliveryRequestDAOImpl extends BaseDAO implements DeliveryRequestDAO {

	@Override
	@Autowired
    @Qualifier("emailSessionFactory")
	public void setSessionFactory(SqlSessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	 
	@Override
	public Class getModelClass() {
		// TODO Auto-generated method stub
		return DeliveryRequest.class;
	}
	
	@Override
	public DeliveryRequest find(int id) {
		SqlSession sqlSession = this.getSession();
		DeliveryRequest request =sqlSession.selectOne("deliveryReq.findById",id);
	    sqlSession.close();
	    return request;
	}
	 
	@Override
	public List<DeliveryRequest> getPendingDelivery() throws CSFEException{
		SqlSession sqlSession = this.getSession();
		List<DeliveryRequest> request =sqlSession.selectList("deliveryReq.findPendingDelivery");
	    sqlSession.close();
	    return request;
	}

	//Insert / Update
	@Override
	public void save(Object deliveryReq) throws CSFEException{
		SqlSession sqlSession = this.getSession();
	    sqlSession.insert("deliveryReq.insertDeliveryReq",deliveryReq);
	    sqlSession.commit();
	    sqlSession.close();
	}
	
	@Override
	public void update(Object deliveryReq) throws CSFEException{
		SqlSession sqlSession = this.getSession();
	    sqlSession.update("deliveryReq.updateDeliveryReq",deliveryReq);
	    sqlSession.commit();
	    sqlSession.close();
	}
	
}
