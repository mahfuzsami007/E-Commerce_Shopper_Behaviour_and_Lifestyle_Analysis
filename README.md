# E-Commerce Shopper Behaviour and Lifestyle Analysis

An end-to-end Big Data analytics pipeline designed to process, aggregate, filter, and extract core consumer demographics and transactional insights from a large-scale dataset containing **1,000,000 records** and **60 high-dimensional feature columns**.

This project implements distributed data processing frameworks using **Hadoop MapReduce (Java)** and **Apache Pig Latin**, optimized to execute seamlessly over an active **Hadoop Distributed File System (HDFS)** topology on a standalone system configuration.

---
## Dataset link: https://www.kaggle.com/datasets/dhrubangtalukdar/e-commerce-shopper-behavior-amazonshopify-based

## 🚀 Key Features & Analysis Capabilities

### 🛠️ Part 1: Apache Hadoop MapReduce (Java Core)
*   **Shopper Volume by Gender:** Custom Mapper dynamically references header arrays to map, distribute, and sum consumer transactions cleanly by gender parameters.
*   **Average Shopper Age:** Custom numerical key aggregator calculates the precise mean runtime value profile across 1 million distributed file records.
*   **Income Level Distribution:** Aggregation pipeline classifying total shopper presence under distinct socioeconomic indicators (`Low`, `Medium`, `High`).

### 🐷 Part 2: Apache Pig Latin Operations (MapReduce Mode)
*   **Geographic Resourcing:** Maps absolute shopper concentration metrics across all registered country regions.
*   **Demographic Filtering:** Strips and aggregates granular user attributes targeting young adults (`Age 18–25`).
*   **Spatial Cluster Breakdown:** Calculates consumer transaction behaviors inside `Urban` vs. `Rural` region frameworks.
*   **Peak E-Commerce Income Metrics:** Finds the top 10 countries with the absolute highest numeric consumer income ceilings.
*   **Composite Segment Summaries:** Uses combined demographic segment grouping matrices (`Gender` and `Income Level`) to evaluate multidimensional marketplace behavior.

---

## 📂 Project Architecture

```text
├── Task_1/
│   └── ShopperGender.java       # Java source file for gender volume tracking
├── Task_2/
│   └── ShopperAge.java          # Java source file for average age analysis
├── Task_3/
│   └── ShopperIncome.java       # Java source file for socioeconomic data mining
├── pig/
│   ├── task1.pig                # Country metrics script
│   ├── task2.pig                # Age slice filtering script
│   ├── task3.pig                # Urban vs Rural region script
│   ├── task4.pig                # Max numeric income extraction script
│   └── task5.pig                # Multidimensional segment tracking script
├── ShopperGender.jar            # Compiled package archive for Task 1
├── ShopperAge.jar               # Compiled package archive for Task 2
├── ShopperIncome.jar            # Compiled package archive for Task 3
└── README.md                    # Project documentation
```

---

## ⚙️ Environment Platform Prerequisites
*   **Operating System:** Windows Standalone System Configuration
*   **Java Environment:** JDK 1.8 (Java 8 Development Kit)
*   **Ecosystem Core:** Apache Hadoop 3.x with configured active HDFS & YARN frameworks
*   **Script Engine:** Apache Pig 0.17.x / 0.18.x

---

## 💻 Step-by-Step Execution Pipeline

### 1. Initialize the HDFS Workspace
Clear out cluster configuration caches and upload your dataset file (`e_commerce_shopper_behaviour_and_lifestyle.csv`) directly into the shared HDFS path fabric:
```cmd
:: Format the master NameNode image configuration
hdfs namenode -format

:: Launch background system cluster daemons
start-dfs.cmd
start-yarn.cmd

:: Upload dataset onto target HDFS pathways
hdfs dfs -mkdir /shopper_project
hdfs dfs -put e_commerce_shopper_behaviour_and_lifestyle.csv /shopper_project/data.csv
```

### 2. Compile and Submit Java MapReduce Jobs
Run these commands to compile source classes against Hadoop dependencies, package them into executable jars, and run them over HDFS:
```cmd
:: Compile Task 1 Source Code
javac -classpath "C:\hadoop\share\hadoop\common\*;C:\hadoop\share\hadoop\common\lib\*;C:\hadoop\share\hadoop\mapreduce\*" -d Task_1 Task_1\ShopperGender.java
jar -cvf ShopperGender.jar -C Task_1 .

:: Submit Job over the Cluster Pathing
hdfs dfs -rm -r /out_gender
hadoop jar ShopperGender.jar ShopperGender /shopper_project/data.csv /out_gender

:: Review Final Generated Results from HDFS
hdfs dfs -cat /out_gender/part-*
```
*(Repeat the compile, package, and execute loop matching the paths inside `Task_2` and `Task_3` directories).*

### 3. Execute Apache Pig Analytics Scripts
Load the core cluster environment variables and execute the scripts directly using default cluster mode operations:
```cmd
:: Load Environment Configurations
set PATH=C:\JAVA\jdk-1.8\bin;C:\pig\bin;%PATH%
set JAVA=C:\JAVA\jdk-1.8\bin\java.exe
set HADOOP_HOME=C:\hadoop
set PIG_CLASSPATH=C:\hadoop\share\hadoop\common\*;C:\hadoop\share\hadoop\common\lib\*;C:\hadoop\share\hadoop\hdfs\*;C:\hadoop\share\hadoop\mapreduce\*

:: Start the background history tracking port daemon
start mapred historyserver

:: Clear destination folder and execute Pig script
hdfs dfs -rm -r /pig_out_t1
pig pig\task1.pig

:: Print output from HDFS
hdfs dfs -cat /pig_out_t1/part-*
```

---
