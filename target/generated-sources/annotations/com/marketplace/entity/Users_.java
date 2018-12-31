package com.marketplace.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-12-06T22:50:52")
@StaticMetamodel(Users.class)
public class Users_ { 

    public static volatile SingularAttribute<Users, String> profilePictureUrl;
    public static volatile SingularAttribute<Users, String> country;
    public static volatile SingularAttribute<Users, String> address;
    public static volatile SingularAttribute<Users, Integer> requestType;
    public static volatile SingularAttribute<Users, String> city;
    public static volatile SingularAttribute<Users, String> requestKey;
    public static volatile SingularAttribute<Users, Date> requestTimestamp;
    public static volatile SingularAttribute<Users, Date> registerTimestamp;
    public static volatile SingularAttribute<Users, String> postalCode;
    public static volatile SingularAttribute<Users, String> ipAddress;
    public static volatile SingularAttribute<Users, String> passwordHash;
    public static volatile SingularAttribute<Users, Date> lastLoginTimestamp;
    public static volatile SingularAttribute<Users, Integer> phoneNumber;
    public static volatile SingularAttribute<Users, String> province;
    public static volatile SingularAttribute<Users, String> name;
    public static volatile SingularAttribute<Users, Integer> id;
    public static volatile SingularAttribute<Users, String> email;

}