# SLURM Data Extration

## Introduction
Two of the 5 V's of data which is Volume and Velocity has been significantly increasing from time to time as modern technology has been skyrocketing and performing better day by day. With respect to the above, High Performance Computing (HPC) services has been increasingly in demand due to its capability to be able to solve complex problems whether it's a virtual problem or a real-life problem.
</br>
A six-months log file has been analysed and extracted from the Simple Linux Utility for Resource Management(SLURM) controller as SLURM is widely used in many of the world's supercomputers and large compute clusters, and it is designed to be highly scalable, fault-tolerant, and easy to use.
</br>
The log file extracted is studied and useful information/metrics has been chosen and extracted to be visualised in the form of a chart (pie chart, bar chart, line chart, etc). The log file has been extracted using Java and visualisation is done through the usage of JFreeChart.
Many challenges have been faced during this task and suitable solutions have been implemented to solve these problems faced with the assistance of HPC services, logical thinking and countless brainstorming.


## Requirement
- To analyse the six-months log file which is extracted from a SLURM controller.
- To study and extract relevant and useful information from the log file.
- To visualise the extracted useful information in a clear and understandable format or chart.
- To identify any issues or problems that have arised and follow up with suitable solutions to overcome the challenges.

## Requirement Analysis
(a)  Number of jobs created in each month.
(b)  Number of backfill jobs in each month.
(c)  Number of jobs done in each month.
(d)  Number of jobs kill successfully/failure in each month.
(e)  Number of jobs by partitions.
(f)  Total number of WEXITSTATUS (successful & unsuccessful).
(g) Total and average cleanup completion time.
(h) Total and average execution time of the jobs submitted to UMHPC.
(i)  Number of error in each month.
(j)  Total type of error and each number of error.
(k)  Number of jobs causing error and the corresponding user. 
(l)  Number of error caused by each user.
(m)  Number of error occur in the time range.

## Output

(a)  Number of jobs created in each month.
![unnamed (12)](https://github.com/SongJuN318/SLURM_Extration/assets/116175959/e9042ba5-841d-452f-894b-0f86da3a4d07)
</br>
![unnamed (13)](https://github.com/SongJuN318/SLURM_Extration/assets/116175959/6bfe63d2-da15-4ea4-be60-7dcc70e06a9d)

(b)  Number of backfill jobs in each month.
![unnamed (14)](https://github.com/SongJuN318/SLURM_Extration/assets/116175959/f42eecc2-18d1-4bf4-8cd5-76b031e0c437)

![unnamed (15)](https://github.com/SongJuN318/SLURM_Extration/assets/116175959/f145fed0-c8f3-4307-90f0-2a6903ba7076)

(c)  Number of jobs done in each month.
![unnamed (16)](https://github.com/SongJuN318/SLURM_Extration/assets/116175959/9c230683-c134-465c-acf8-9071c1a660cb)

![unnamed (17)](https://github.com/SongJuN318/SLURM_Extration/assets/116175959/3c725cae-c4b1-4e0e-b96b-6fa49a6b5251)

(d)  Number of jobs kill successfully/failure in each month.
![unnamed (18)](https://github.com/SongJuN318/SLURM_Extration/assets/116175959/f97fd89a-98c1-4455-956d-8a015a6ab677)

![unnamed (19)](https://github.com/SongJuN318/SLURM_Extration/assets/116175959/ee18d20b-5698-451c-97a3-5473b42cad9f)

![unnamed (20)](https://github.com/SongJuN318/SLURM_Extration/assets/116175959/c864588d-66a4-4239-b85a-9813703625e8)

![unnamed (21)](https://github.com/SongJuN318/SLURM_Extration/assets/116175959/d83691b9-e90e-4d66-8057-f4904f611189)

(e)  Number of jobs by partitions.
![unnamed (22)](https://github.com/SongJuN318/SLURM_Extration/assets/116175959/cc1832c3-091f-42e3-8d65-c7a664d13d91)

![unnamed (23)](https://github.com/SongJuN318/SLURM_Extration/assets/116175959/450da866-e0e1-4e26-9094-166749fb55e1)

(f)  Total number of WEXITSTATUS (successful & unsuccessful).
![unnamed (24)](https://github.com/SongJuN318/SLURM_Extration/assets/116175959/1b6a6acc-0d64-4e40-b2f0-691424e3b3eb)

![unnamed (25)](https://github.com/SongJuN318/SLURM_Extration/assets/116175959/e1f977a1-89a6-43a2-b068-116e17f81d08)

(g) Total and average cleanup completion time.
![unnamed (26)](https://github.com/SongJuN318/SLURM_Extration/assets/116175959/4055104b-e6ec-42a5-8dda-c38a6cdb7f34)

![unnamed (27)](https://github.com/SongJuN318/SLURM_Extration/assets/116175959/3daa2a64-89e9-491d-91cc-ee521cf0aada)

(h) Total and average execution time of the jobs submitted to UMHPC.
![unnamed (28)](https://github.com/SongJuN318/SLURM_Extration/assets/116175959/ad677970-ddc9-4822-8c1b-7fccb1cd70a3)

![unnamed (29)](https://github.com/SongJuN318/SLURM_Extration/assets/116175959/533793b3-996c-4408-b6c9-2e7f04f927dd)

(i)  Number of error in each month.
![unnamed (30)](https://github.com/SongJuN318/SLURM_Extration/assets/116175959/13b30e25-a839-4ae4-bde8-19dfc1e790be)

(j)  Total type of error and each number of error
![unnamed (31)](https://github.com/SongJuN318/SLURM_Extration/assets/116175959/2b7c13fe-191a-4e88-895f-056cda0485d4)

(k)  Number of jobs causing error and the corresponding user. 
![unnamed (32)](https://github.com/SongJuN318/SLURM_Extration/assets/116175959/3cf53477-c8bf-4a99-8930-c97b4133d249)

(l)  Number of error caused by each user.
![unnamed (33)](https://github.com/SongJuN318/SLURM_Extration/assets/116175959/d65f0762-41b5-4751-85a5-b96d1b65be7e)

(m)  Number of error occur in the time range.
![unnamed (34)](https://github.com/SongJuN318/SLURM_Extration/assets/116175959/bcbf2530-d44d-4b95-9b39-a7130a378652)

![unnamed (35)](https://github.com/SongJuN318/SLURM_Extration/assets/116175959/d0266997-7f15-4837-a165-3d33eac22ac7)
