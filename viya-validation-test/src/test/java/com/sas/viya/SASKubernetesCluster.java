package com.sas.viya;

import static org.testng.Assert.assertEquals;

import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.fabric8.kubernetes.api.model.ListOptionsBuilder;
import io.fabric8.kubernetes.api.model.PodList;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientException;
import io.fabric8.kubernetes.api.model.NodeList;

public class SASKubernetesCluster extends BaseTest {
	
	@Parameters("namespace")
	@Test(priority=1, testName = "SASPodsValidation", groups = {"smoke", "functional"})
	public void PodsValidation(String namespace) throws Exception {
		try (KubernetesClient client = new DefaultKubernetesClient()) {
			PodList podList = client.pods().inNamespace(namespace).list(new ListOptionsBuilder().withLimit(5L).build());
		    podList.getItems().forEach(obj -> System.out.println(obj.getMetadata().getName()));
		    int actualValue = client.pods().inNamespace(namespace).list(new ListOptionsBuilder().build()).getItems().size();
		    System.out.println(actualValue);
		    Assert.assertTrue(actualValue >= 115, "Pod count not correct");
			extentTest.pass("Assertion is passed for Pods Valiation in Cluster");
		}
		catch (KubernetesClientException e) {
			
		}
	}
	
	@Test(priority=2, testName = "SASNodesValidation", groups = {"smoke", "functional"})
	public void NodesValidation() throws Exception {
		try (KubernetesClient client = new DefaultKubernetesClient()) {
			NodeList nodeList = client.nodes().list(new ListOptionsBuilder().withLimit(5L).build());
			nodeList.getItems().forEach(obj -> System.out.println(obj.getMetadata().getName()));
			int actualValue = client.nodes().list(new ListOptionsBuilder().build()).getItems().size();
			System.out.println(actualValue);
		    Assert.assertTrue(actualValue >= 5, "Node count not correct");
			extentTest.pass("Assertion is passed for Nodes Validation in Cluster");
		}
		catch (KubernetesClientException e) {
			
		}
	}

}
