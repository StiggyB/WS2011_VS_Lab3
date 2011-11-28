package mware_lib;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import mware_lib.messages.RebindMessage;
import mware_lib.messages.ResolveMessage;
import mware_lib.messages.UnbindMessage;
import mware_lib.tcp_advanced.Client;
import branch_access.Manager;
import branch_access.ManagerProxy;
import branch_access.ManagerSkeleton;
import cash_access.AccountProxy;
import cash_access.AccountSkeleton;

public class LocalNameService extends NameService {

    private String host;
    private int port;
    private Client client;
    private static int skeletonPort = 2500;

    public LocalNameService(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public void rebind(Object servant, String name) {
        try {
            Class<?> objType = (generateSkeletons(servant, name));
            RemoteInfo rInfo = new RemoteInfo(InetAddress.getLocalHost()
                    .getHostAddress(), skeletonPort, objType);
            RebindMessage rbMsg = new RebindMessage(rInfo, name);
            client = new Client(this.host, this.port);
            client.send(rbMsg);
        } catch (UnknownHostException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            try {
                skeletonPort++;
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Object resolve(String name) {
        Object resultObj = null;
        try {
            ResolveMessage resMsg = new ResolveMessage(name);
            client = new Client(this.host, this.port);
            Thread.sleep(500);
            client.send(resMsg);
            resultObj = client.receive();
            if (resultObj instanceof RemoteInfo) {
                RemoteInfo rMsg = (RemoteInfo) resultObj;
                resultObj = generateObjectRef(rMsg.getType(), name,
                        rMsg.getHost(), rMsg.getPort());
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultObj;
    }

    public void unbind(String name) {
        UnbindMessage uMsg = new UnbindMessage(name);
        try {
            client = new Client(this.host, this.port);
            client.send(uMsg);
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Class<?> generateSkeletons(Object remoteObj, String name) {
        Class<?> remoteType = remoteObj.getClass();
        Class<?> resultObj = null;
        if (Account.class.equals(remoteType.getSuperclass())) {
            resultObj = Account.class;
            AccountSkeleton accSkel = new AccountSkeleton(skeletonPort,
                    (Account) remoteObj);
            accSkel.start();
        } else if (Manager.class.equals(remoteType.getSuperclass())) {
            resultObj = Manager.class;
            ManagerSkeleton manSkel;
            try {
                manSkel = new ManagerSkeleton(skeletonPort, (Manager) remoteObj);
                manSkel.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultObj;
    }

    public Object generateObjectRef(Class<?> remoteType, String name,
            String skelHost, int skelPort) {
        Object resultObj = null;
        if (Account.class.equals(remoteType)) {
            resultObj = new AccountProxy(skelHost, skelPort, name);
        } else if (Manager.class.equals(remoteType)) {
            resultObj = new ManagerProxy(skelHost, skelPort, name);
        }
        return resultObj;
    }

}
