echo "02-run-dump-with-has-file.sh"

# folder_import_dump_path="/opt/oracle/admin/ORCLCDB/dpdump/"
# folder_dump_file_path="/dump/"

# first_dmp_file=""
# ls "$folder_dump_file_path"/*.dmp 2>/dev/null | while read -r file; do
#     first_dmp_file="$file"
#     break
# done

# if [ -n "$first_dmp_file" ]; then
#     cp -P /dump/* "$folder_import_dump_path"
#     # # get File name
#     file_name="${first_dmp_file[0]}"

#     echo "file with .dmp found in: $first_dmp_file / name: $file_name"
#     impdp SYSTEM/1234@localhost:1521/ORCLCDB DUMPFILE="$file_name" SCHEMAS=LPORTAL
# else
#     echo "No .dmp files found in the specified folder."
# fi





