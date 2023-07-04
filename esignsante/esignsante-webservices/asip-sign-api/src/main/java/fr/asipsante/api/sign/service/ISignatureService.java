/**
 * (c) Copyright 1998-2020, ANS. All rights reserved.
 */
package fr.asipsante.api.sign.service;

import fr.asipsante.api.sign.bean.parameters.SignatureParameters;
import fr.asipsante.api.sign.bean.rapports.RapportSignature;
import fr.asipsante.api.sign.utils.AsipSignException;

/**
 * L’interface SignatureService a pour but d’offrir une exposition transverse
 * des verifications de signature d'un document. Elle propose les opérations
 * suivantes :
 *
 * • Chargement des bundles de CAs (Certificate Authority) en mémoire depuis un
 * fichier • Chargement des CRLs (Certificate Revocation List) en mémoire depuis
 * un fichier • Validation d'une signature d'un document au format XMLDSIG •
 * Validation d'une signature d'un document au format XADES profil BASELINE-B
 *
 * Le chargement des CAs/CRLs est compatible avec le composant CompCACRL.
 *
 * Chaque opération retourne un ou plusieurs rapports résumant les actions
 * effectuées et, si il y a lieu, la liste des erreurs rencontrées.
 *
 * @author Sopra Steria
 */
public interface ISignatureService {

	/**
	 * Vérification de signature d'un document au format XADES (ou PADES) profil BASELINE-B.
	 *
	 * La méthode verifierBundleCRLNonNull construit le rapport de validation des
	 * certificats et signatures, avec les contrôles suivants:
	 *
	 * • Existence du certificat : le certificat utilisé pour signer doit être
	 * inclus dans la signature à l’aide de la balise KeyInfo/X509Certificate. •
	 * Contrôle des certificats d’autorité afin de garantir que le certificat a été
	 * émis par une autorité de certification digne de confiance. • Contrôle de
	 * validité des CRLs. • Contrôle de non-révocation du certificat. Si le
	 * certificat existe dans la liste des CRLs, ou si la date de signature est
	 * égale ou postérieure à la date de révocation, alors le certificat est
	 * considéré comme révoqué. • Vérification de la date de signature : elle doit
	 * être strictement antérieure à la date d’expiration de la signature. •
	 * Vérification que le certificat contient l’usage de la signature
	 * (non-répudiation).
	 *
	 * • Contrôle du format de la signature. • Vérification de la non-corruption du
	 * document signé : le document ne doit pas avoir été modifié après signature. •
	 * Existence de la date de signature : la date doit être présente dans la balise
	 * SigningTime.
	 *
	 * @param doc    the doc
	 * @param params the params
	 * @return un rapport de validation regroupant le résultat de l'ensemble des
	 *         contrôles effectués et des éventuelles erreurs rencontrées
	 * @throws AsipSignException the asip sign exception
	 */
	RapportSignature signXADESBaselineB(String doc, SignatureParameters params) throws AsipSignException;

	/**
	 * Sign xades baseline b rapport signature.
	 *
	 * @param doc    the doc
	 * @param params the params
	 * @return the rapport signature
	 * @throws AsipSignException the asip sign exception
	 */
	RapportSignature signXADESBaselineB(byte[] doc, SignatureParameters params) throws AsipSignException;

	/**
	 * Sign pades baseline b rapport signature.
	 *
	 * @param doc    the doc
	 * @param params the params
	 * @return the rapport signature
	 * @throws AsipSignException the asip sign exception
	 */
	RapportSignature signPADESBaselineB(byte[] doc, SignatureParameters params) throws AsipSignException;

	/**
	 * Sign XML dsig.
	 *
	 * @param doc    the doc
	 * @param params the params
	 * @return the rapport signature
	 * @throws AsipSignException the asip sign exception
	 */
	RapportSignature signXMLDsig(String doc, SignatureParameters params) throws AsipSignException;

	/**
	 * Sign xml dsig rapport signature.
	 *
	 * @param doc    the doc
	 * @param params the params
	 * @return the rapport signature
	 * @throws AsipSignException the asip sign exception
	 */
	RapportSignature signXMLDsig(byte[] doc, SignatureParameters params) throws AsipSignException;
	
	/**
	 * Sign FSE (Feuille de Soin Electronique).
	 * Specific signature: detached no aDES without original document. 
     * @param hashDoc    the hash of the doc to be signed 
	 * @param idFacturation  idFacturation du PS. Paramètre à ajouter aux CMSATtributs
	 * @param typeFlux    T ou R.  Paramètre à ajouter aux CMSATtributs
	 * @param params the params classe mère SignatureParameters
	 * @return the rapport signature
	 * @throws AsipSignException the asip sign exception
	 */
	RapportSignature signFSE(byte[] hashDoc, String idFacturation, String typeFlux, SignatureParameters params) throws AsipSignException;

}
