package com.cnpc.curator.cal;

import org.apache.curator.framework.api.ACLProvider;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName AClFactory
 * @Description TODO
 * @Autor liuhui
 * @Date
 * @Version
 */
public class AClFactory implements ACLProvider {
    private List<ACL> acls;
    @Override
    public List<ACL> getDefaultAcl() {
        if(acls ==null){
            ArrayList<ACL> acl = ZooDefs.Ids.CREATOR_ALL_ACL;
            acl.clear();
            acl.add(new ACL(ZooDefs.Perms.ALL, new Id("auth", "admin:admin") ));
            this.acls = acl;
        }
        return acls;
    }

    @Override
    public List<ACL> getAclForPath(String path) {
        return getDefaultAcl();
    }
}
