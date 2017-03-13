package com.example.method;

import java.math.BigInteger;
import java.security.SecureRandom;

import org.bouncycastle.crypto.generators.ECKeyPairGenerator;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECKeyGenerationParameters;
import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.ECFieldElement;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.math.ec.ECFieldElement.Fp;

public class SM2 
{
	//测试参数
//	public static final String[] ecc_param = {
//	    "8542D69E4C044F18E8B92435BF6FF7DE457283915C45517D722EDB8B08F1DFC3", 
//	    "787968B4FA32C3FD2417842E73BBFEFF2F3C848B6831D7E0EC65228B3937E498", 
//	    "63E4C6D3B23B0C849CF84241484BFE48F61D59A5B16BA06E6E12D1DA27C5249A", 
//	    "8542D69E4C044F18E8B92435BF6FF7DD297720630485628D5AE74EE7C32E79B7", 
//	    "421DEBD61B62EAB6746434EBC3CC315E32220B3BADD50BDC4C4E6C147FEDD43D", 
//	    "0680512BCBB42C07D47349D2153B70C4E5D7FDFCBFA36EA1A85841B9E46E09A2" 
//	};
	
	//正式参数
	public static String[] ecc_param = { 
		"FFFFFFFEFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF00000000FFFFFFFFFFFFFFFF",
		"FFFFFFFEFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF00000000FFFFFFFFFFFFFFFC",
		"28E9FA9E9D9F5E344D5A9E4BCF6509A7F39789F515AB8F92DDBCBD414D940E93",
		"FFFFFFFEFFFFFFFFFFFFFFFFFFFFFFFF7203DF6B21C6052B53BBF40939D54123",
		"32C4AE2C1F1981195F9904466A39C9948FE30BBFF2660BE1715A4589334C74C7",
		"BC3736A2F4F6779C59BDCEE36B692153D0A9877CC62A474002DF32E52139F0A0"
	};

	public static SM2 Instance() 
	{
		return new SM2();
	}

	public final BigInteger ecc_p;
	public final BigInteger ecc_a;
	public final BigInteger ecc_b;
	public final BigInteger ecc_n;
	public final BigInteger ecc_gx;
	public final BigInteger ecc_gy;
	public final ECCurve ecc_curve;
	public final ECPoint ecc_point_g;
	public final ECDomainParameters ecc_bc_spec;
	public final ECKeyPairGenerator ecc_key_pair_generator;
	public final ECFieldElement ecc_gx_fieldelement;
	public final ECFieldElement ecc_gy_fieldelement;

	public SM2() 
	{
		this.ecc_p = new BigInteger(ecc_param[0], 16);
		this.ecc_a = new BigInteger(ecc_param[1], 16);
		this.ecc_b = new BigInteger(ecc_param[2], 16);
		this.ecc_n = new BigInteger(ecc_param[3], 16);
		this.ecc_gx = new BigInteger(ecc_param[4], 16);
		this.ecc_gy = new BigInteger(ecc_param[5], 16);

		this.ecc_gx_fieldelement = new Fp(this.ecc_p, this.ecc_gx);
		this.ecc_gy_fieldelement = new Fp(this.ecc_p, this.ecc_gy);

		this.ecc_curve = new ECCurve.Fp(this.ecc_p, this.ecc_a, this.ecc_b);
		this.ecc_point_g = new ECPoint.Fp(this.ecc_curve, this.ecc_gx_fieldelement, this.ecc_gy_fieldelement);

		this.ecc_bc_spec = new ECDomainParameters(this.ecc_curve, this.ecc_point_g, this.ecc_n);

		ECKeyGenerationParameters ecc_ecgenparam;
		ecc_ecgenparam = new ECKeyGenerationParameters(this.ecc_bc_spec, new SecureRandom());

		this.ecc_key_pair_generator = new ECKeyPairGenerator();
		this.ecc_key_pair_generator.init(ecc_ecgenparam);
	}
}
