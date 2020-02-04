---
author: Pascal Fares
description: |
    Secure Socket Layer (SSL) is a protocol that provides security for
    communications between client and server by implementing encrypted data
    and certificate-based authentication. If you’re using…
parsely-post-id: 65cd26290b70
referrer: 'unsafe-url'
robots: 'index,follow'
theme-color: '\#000000'
title:
- |
    A Step-By-Step Guide to Securing a Tomcat Server With LetsEncrypt SSL
    Certificate
- |
    A Step-By-Step Guide to Securing a Tomcat Server With LetsEncrypt SSL
    Certificate
twitter:app:id:iphone: 828256236
twitter:app:name:iphone: Medium
twitter:app:url:iphone: 'medium://p/65cd26290b70'
twitter:card: 'summary\_large\_image'
twitter:image:src: 'https://miro.medium.com/max/480/1\*iWXzOAJfrTCB5CJwV75B7A.jpeg'
twitter:site: '@Medium'
viewport: 'width=device-width,minimum-scale=1,initial-scale=1'
---

<div id="root">

<div class="a b c">

<div>


<div class="section cu cv cw cx cy">

<div class="n p">

<div class="ac ae af ag ah cz aj ak">

<div>

<div id="b7a3"
class="da db dc bk dd b de df dg dh di dj dk dl dm dn do">

A Step-By-Step Guide to Securing a Tomcat Server With LetsEncrypt SSL Certificate 
=================================================================================

</div>

Secure Socket Layer (SSL) is a protocol that provides security for
communications between client and server by implementing encrypted data
and certificate-based authentication.

If you’re using Apache Tomcat as a Server for your web-application ,
chances are that at least some of the data you’re handling is sensitive,
and SSL is an easy way to offer your users security. But the
configuration process and SSL itself can be a little confusing for
first-time users.

There are many CA from which you can get a certificate, but almost all
of them will cost you money. But, with Let’s Encrypt you can get a valid
SSL certificate for your domain at no cost.

This guide will break down the messy process of installing a SSL
certificate for tomcat server into easily understandable pieces:

Step 1 — Prerequisites
======================

Before starting work on this task, I assume you already have:

-   Running Centos system with sudo privileges shell access.
-   A domain name registered and pointed to your server’s public IP
    address. For this tutorial, we use example.com and
    [www.example.com,](http://www.example.com%2C/){.at .cg .ht .hu .hv
    .hw} which is pointed to our server.
-   Recent version of JAVA installed.
-   Recent version of tomcat server installed in your .
-   Have port 80 and 8443 open in your
    [firewall](https://linuxize.com/post/how-to-setup-a-firewall-with-firewalld-on-centos-7/)
-   Have Openssl installed.

Step 2— Install Certbot
=======================

The certbot package is provided by EPEL. If the [EPEL
repository](https://linuxize.com/post/how-to-enable-epel-repository-on-centos/){.at
.cg .ht .hu .hv .hw} is not installed on your system, you can install it
using the following command:

```
sudo yum install epel-release
```

Once the EPEL repository is enabled, install the certbot package by
typing:

```
sudo yum install certbot
```

If you have an active firewall, e.g firewalld**,** open https port on
the firewall.

```
# firewall-cmd --add-service https --permanent
# firewall-cmd --reload
```

Step 3—Generate keypair and get certificate against the domain using Certbot
============================================================================

Once the LetsEncrypt (CA) verifies the authenticity of your domain, SSL
certificate will be issued. For generating keypair and getting a SSL
certificate against that keypair for your domain we need to type the
following command:

```
sudo certbot certonly --standalone -d www.example.com
```

If everything goes fine. A new ssl will be issued at below location.
Navigate to below directory and view files.

```
cd /etc/letsencrypt/live/example.com
ls
```
*Files List:*

```
  cert.pem
  chain.pem
  fullchain.pem
  privkey.pem
```

Step 4 — Convert keypair + certificate to Java Keystore 
=======================================================

At first create a PKCS12 that contains both your full chain and the
private key. You need to have openssl installed for that.

```
openssl pkcs12 -export -out /tmp/example.com_fullchain_and_key.p12 \
    -in /etc/letsencrypt/live/example.com/fullchain.pem \
    -inkey /etc/letsencrypt/live/example.com/privkey.pem \
    -name tomcat
```

Then convert that PKCS12 to a JKS, using java\`s keytool

```
keytool -importkeystore \
    -deststorepass samplePassword -destkeypass samplePassword -destkeystore /tmp/example.com.jks \
    -srckeystore /tmp/example.com_fullchain_and_key.p12  -srcstoretype PKCS12 -srcstorepass samplePassword \
    -alias tomcat
```

Replace `samplePassword` with your password

Step 5— Configure Tomcat with the Java Keystore
===============================================

Now go to your tomcat application and open your server.xml file

```
# vim /etc/tomcat/conf/server.xml
```

Ensure the following section is commented out

```
<!---
    <Connector port="8080" protocol="HTTP/1.1"
            connectionTimeout="20000"
            redirectPort="8443" />
    -->
```

Configure connector to use a shared thread pool

```
<Connector executor="tomcatThreadPool"
            port="8080" protocol="HTTP/1.1"
            connectionTimeout="20000"
            redirectPort="8443" />
```

Next is to define SSL HTTP/1.1 Connector on port 8443

```
<Connector port="8443" protocol="org.apache.coyote.http11.Http11Protocol"
            maxThreads="150" SSLEnabled="true" scheme="https" secure="true"
            keystoreFile="/tmp/example.com.jks"
            keystorePass="samplePassword"
            clientAuth="false" sslProtocol="TLS" />
```

With above configuration, http to https redirect will be done
automatically for the application.

Now just Stop and Start Apache Tomcat and you are done.

<div class="il im fz in ak">

<div class="cl cm ik">

<div class="fy r fz ga">

<div class="io r">

<div class="mc nv cp t u fv ak eh fw fx">

![](./A%20Step-By-Step%20Guide%20to%20Securing%20a%20Tomcat%20Server%20With%20LetsEncrypt%20SSL%20Certificate_files/1_-ui3paUVp1PJBhEwjWdgFg.png)

</div>

![](https://miro.medium.com/max/1426/1*-ui3paUVp1PJBhEwjWdgFg.png)

</div>

</div>

</div>

</div>

Your tomcat server along with all the application that runs on it is ssl
secured.

</div>

</div>

</div>

------------------------------------------------------------------------

<div class="section cu cv cw cx cy">

<div class="n p">

<div class="ac ae af ag ah cz aj ak">

*The author is a seasoned professional in cryptography and security. He
is the cofounder of ObboyLabs . He also a consultant to BCC, ICT
Division Bangladesh & leads their Certificate Authority team.*

</div>

</div>

</div>

</div>

<div class="ft ct iz s ak jg je jh" data-test-id="post-sidebar">

<div class="n p">

<div class="ac ae af ag ah ai aj ak">

<div class="ji n co">

<div class="ct">

<div class="jj jk jl n">

<div class="n o">

<div class="jm r fz">

[](https://medium.com/m/signin?operation=register&redirect=https%3A%2F%2Fmedium.com%2F%40mashrur123%2Fa-step-by-step-guide-to-securing-a-tomcat-server-with-letsencrypt-ssl-certificate-65cd26290b70&source=post_sidebar-----65cd26290b70---------------------clap_sidebar-)
<div class="ba jn jo jp jq jr js jt ju jv jw">

</div>

</div>



</div>

</div>

</div>

</div>

</div>

</div>

</div>

<div class="ft ct iz s ja jb jc jd je jf">

</div>

<div>

<div class="jz fs n co p">

<div class="n p">

<div class="ac ae af ag ah cz aj ak">

<div class="n ka">

</div>

<div class="n o ka">

</div>

<div class="kb r">

-   [Ssl](https://medium.com/tag/ssl){.kf .kg .cg .bo .r .hy .kh .a .b
    .fb}
-   [Security](https://medium.com/tag/security){.kf .kg .cg .bo .r .hy
    .kh .a .b .fb}
-   [Tomcat](https://medium.com/tag/tomcat){.kf .kg .cg .bo .r .hy .kh
    .a .b .fb}
-   [Lets Encrypt](https://medium.com/tag/lets-encrypt){.kf .kg .cg .bo
    .r .hy .kh .a .b .fb}
-   [Ssl Certificate](https://medium.com/tag/ssl-certificate){.kf .kg
    .cg .bo .r .hy .kh .a .b .fb}

</div>

<div class="ki n dv ab">

<div class="n o">

<div class="kj r fz">

[](https://medium.com/m/signin?operation=register&redirect=https%3A%2F%2Fmedium.com%2F%40mashrur123%2Fa-step-by-step-guide-to-securing-a-tomcat-server-with-letsencrypt-ssl-certificate-65cd26290b70&source=post_actions_footer-----65cd26290b70---------------------clap_footer-)
<div
class="c kk dz n o kl fz km kn ko kp kq kr ks kt ku kv kw kx ky kz">

<div
class="ba jn jo jp jq jr la jt o gf dz n p lb u fv cp t ak ju jv jw lc">

</div>

</div>

</div>

<div class="jx r">

<div class="jy">

8 claps

</div>

</div>

</div>

<div class="n o">

<div class="fk r ar">

</div>

<div class="fk r ar">

</div>

<div class="fk r ar">

</div>

<div class="cf" aria-hidden="true">

<div class="r ar">

</div>

</div>

</div>

</div>

<div class="ld le lf kb r ab">

<div class="lg lh r fz">




[]{.r}
<div class="lo r lp">



</div>

<div class="lo ls n lp">

<div class="ak n o dv">


<div class="r g">



</div>

</div>

</div>



</div>

</div>


</div>

</div>

</div>

<div class="me r mf ab">

<div class="n p">

<div class="ac ae af ag ah ai aj ak">

<div class="pt r pu">



<div class="py n pz ka qa qb qc qd qe qf qg qh qi qj qk ql qm qn qo">

<div
class="qp qq qr qs qt qu qv qw qx qy qz ra rb rc rd re rf rg rh ri rj">

<div class="ak fv">

<div class="r rk">

<div
class="rl rm qa qb qc rn ro qd qe qf rp rq qg qh qi rr rs qj qk ql rt ru qm qn qo n ka">

<div
class="qp qq qr qs qt qu rv rw qx qy rx ry rb rc rz sa rf rg sb sc rj">

<div class="sd r se f">

#### Also tagged Ssl {#also-tagged-ssl .bj .ef .eg .bl .bo}

</div>

<div class="sf r sg pu">

[](https://medium.com/@osamaaamer/configuring-ssl-for-hasura-graphql-deployed-on-digitalocean-kubernetes-1609a9eb36f0?source=post_recirc---------0------------------){.at
.au .av .aw .ax .ay .az .ba .bb .bc .bd .be .bf .bg .bh .bi .r}
<div class="sh fz">

<div class="fv cp ak">

<div class="si r sj sk fv ak sl oa">

</div>

</div>

</div>

</div>

</div>


</div>

</div>

</div>

</div>

</div>

</div>

<div
class="qp qq qr qs qt qu qv qw qx qy qz ra rb rc rd re rf rg rh ri rj">

<div class="ak fv">


<div
class="qp qq qr qs qt qu qv qw qx qy qz ra rb rc rd re rf rg rh ri rj">

<div class="ak fv">

<div class="r rk">

<div
class="rl rm qa qb qc rn ro qd qe qf rp rq qg qh qi rr rs qj qk ql rt ru qm qn qo n ka">

<div
class="qp qq qr qs qt qu rv rw qx qy rx ry rb rc rz sa rf rg sb sc rj">

<div class="sd r se f">

#### Related reads {#related-reads .bj .ef .eg .bl .bo}

</div>

<div class="sf r sg pu">

[](https://medium.com/better-programming/customize-your-mysql-database-in-docker-723ffd59d8fb?source=post_recirc---------2------------------){.at
.au .av .aw .ax .ay .az .ba .bb .bc .bd .be .bf .bg .bh .bi .r}
<div class="sh fz">

<div class="fv cp ak">

<div class="tc r sj sk fv ak sl oa">

</div>

</div>

</div>

</div>

</div>

<div
class="qp qq qr qs qt qu rv rw qx qy rx ry rb rc rz sa rf rg sb sc rj">

<div class="sf r">

<div class="sm ap h sn">

#### Related reads {#related-reads-1 .bj .ef .eg .bl .bo}

</div>

[](https://medium.com/better-programming/customize-your-mysql-database-in-docker-723ffd59d8fb?source=post_recirc---------2------------------)
### Customize your MySQL Database in Docker {#customize-your-mysql-database-in-docker .dc .q .dd .so .bk .sp .sq .sr}

</div>

<div class="n o dv">

<div class="ss r st">

<div class="o n">

<div>

[![Lorenz
Vanthillo](./A%20Step-By-Step%20Guide%20to%20Securing%20a%20Tomcat%20Server%20With%20LetsEncrypt%20SSL%20Certificate_files/1_pPLuhaIv_JKl6PXaCGPWGA.png){.r
.dz .sv .su width="40"
height="40"}](https://medium.com/@lvthillo?source=post_recirc---------2------------------)

</div>

<div class="ec ak r">

<div class="n">

<div style="flex: 1 1 0%;">

[]{.bj .b .bk .bl .bm .bn .r .dc .q}
<div class="ch n o ee">

[[Lorenz
Vanthillo](https://medium.com/@lvthillo?source=post_recirc---------2------------------){.at
.au .av .aw .ax .ay .az .ba .bb .bc .en .bf .bg .bh .bi}[ in [Better
Programming](https://medium.com/better-programming?source=post_recirc---------2------------------){.at
.au .av .aw .ax .ay .az .ba .bb .bc .en .bf .bg .bh .bi}]{}]{.bj .ef .eg
.bl .eh .ei .ej .ek .el .em .dc}

</div>

</div>

</div>

[[]{.bj .ef .eg .bl .eh .ei .ej .ek .el .em .bo}]{.bj .b .bk .bl .bm .bn
.r .bo .bp}
<div>

[Mar 31,
2018](https://medium.com/better-programming/customize-your-mysql-database-in-docker-723ffd59d8fb?source=post_recirc---------2------------------){.at
.au .av .aw .ax .ay .az .ba .bb .bc .en .bf .bg .bh .bi} · 4 min read

</div>

</div>

</div>

</div>

<div class="n o">

<div class="n o">

<div class="jm r fz">

[](https://medium.com/m/signin?operation=register&redirect=https%3A%2F%2Fmedium.com%2F%40mashrur123%2Fa-step-by-step-guide-to-securing-a-tomcat-server-with-letsencrypt-ssl-certificate-65cd26290b70&source=post_recirc-----723ffd59d8fb----2-----------------clap_preview-)
<div class="ba jn jo jp jq jr js nu ju jv jw">

</div>

</div>

<div class="jx r">

<div class="jy">

#### 631 {#section-1 .bj .ef .eg .bl .bo}

</div>

</div>

</div>

<div class="sz ec ss ta pc r">

</div>

<div>

<div class="cf" role="tooltip" aria-hidden="true" aria-describedby="3"
aria-labelledby="3">

</div>

</div>

</div>

</div>

</div>

</div>

</div>

</div>

</div>

</div>

</div>

</div>

</div>

</div>

</div>

</div>

<div class="mg r mh mi">

<div
class="section cl cm ak ce r mj mk ml mm mn mo mp mq mr ms mt mu mv mw mx">

<div class="my mz lg n dv g">

<div class="na n dv">

<div class="nb r nc">

<div class="nd r">

[](https://medium.com/about?autoplay=1&source=post_page-----65cd26290b70----------------------){.at
.au .av .aw .ax .ay .az .ba .bb .bc .ne .nf .bf .bg .ng .nh}
#### Discover Medium {#discover-medium .ni .nj .nk .bj .gw .bk .ly .nl .nm .r}

</div>

[Welcome to a place where words matter. On Medium, smart voices and
original ideas take center stage - with no ads in sight.
[Watch](https://medium.com/about?autoplay=1&source=post_page-----65cd26290b70----------------------){.at
.au .av .aw .ax .ay .az .ba .bb .bc .bf .bg .ng .nh .np}]{.bj .b .bk .bl
.bm .bn .r .nn .no}

</div>

<div class="nb r nc">

<div class="nq r">

[](https://medium.com/topics?source=post_page-----65cd26290b70----------------------){.at
.au .av .aw .ax .ay .az .ba .bb .bc .ne .nf .bf .bg .ng .nh}
#### Make Medium yours {#make-medium-yours .ni .nj .nk .bj .gw .bk .ly .nl .nm .r}

</div>

[Follow all the topics you care about, and we’ll deliver the best
stories for you to your homepage and inbox.
[Explore](https://medium.com/topics?source=post_page-----65cd26290b70----------------------){.at
.au .av .aw .ax .ay .az .ba .bb .bc .bf .bg .ng .nh .np}]{.bj .b .bk .bl
.bm .bn .r .nn .no}

</div>

<div class="nb r nc">

<div class="nd r">

[](https://medium.com/membership?source=post_page-----65cd26290b70----------------------){.at
.au .av .aw .ax .ay .az .ba .bb .bc .ne .nf .bf .bg .ng .nh}
#### Become a member {#become-a-member .ni .nj .nk .bj .gw .bk .ly .nl .nm .r}

</div>

[Get unlimited access to the best stories on Medium — and support
writers while you’re at it. Just \$5/month.
[Upgrade](https://medium.com/membership?source=post_page-----65cd26290b70----------------------){.at
.au .av .aw .ax .ay .az .ba .bb .bc .bf .bg .ng .nh .np}]{.bj .b .bk .bl
.bm .bn .r .nn .no}

</div>

</div>

</div>

<div class="n o dv">

[]{.bj .b .bk .bl .bm .bn .r .nn .no}
<div class="nr ns n dv nt an">

[About](https://medium.com/about?autoplay=1&source=post_page-----65cd26290b70----------------------){.at
.au .av .aw .ax .ay .az .ba .bb .bc .en .bf .bg .ng
.nh}[Help](https://help.medium.com/?source=post_page-----65cd26290b70----------------------){.at
.au .av .aw .ax .ay .az .ba .bb .bc .en .bf .bg .ng
.nh}[Legal](https://medium.com/policy/9db0094a1e0f?source=post_page-----65cd26290b70----------------------){.at
.au .av .aw .ax .ay .az .ba .bb .bc .en .bf .bg .ng .nh}

</div>

</div>

</div>

</div>

</div>

</div>
