#Fusion-UAST

Fusion-UAST is a unified application security testing tool that is a proof of concept of automated application security testing by running full testing with both SAST and DAST tools.


**This tool was developed with the help of AI (Copilot) and other sources such as StackOverflow and Youtube**

There are some dependencies that you require in order to run this software(these commands are for linux, as this software was developed on linux):

### For the DAST commands you need:

- syft: curl -sSfL https://raw.githubusercontent.com/anchore/syft/main/install.sh | sh -s -- -v

- grype: curl -sSfL https://raw.githubusercontent.com/anchore/grype/main/install.sh | sh -s -- -v

### For the SAST commands you need:

- pip install semgrep 
    (ensure you have python installed)
    (sudo apt update)
    (sudo apt install python3)
    (sudo apt install python3-pip)

### For the apk decompiler you require:
sudo apt install jadx
