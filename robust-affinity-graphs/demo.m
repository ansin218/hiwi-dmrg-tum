%% The demo for constructing random forest based affinity matrices
% 
% @Author: Xiatian (Eddy) Zhu
% @Date: 17 June. 2014

addpath('random forest');
addpath('SPClust');

%% Load data
% Use pendigits.mat file to obtain results
load('pendigits');

% The pendigits.mat file is not exactly similar to the original data.mat
% file. There are lots of variables in the mat file and the data types are
% different. We only require the values from X and Y. The following
% pendigits file has single data type and we must convert it to double data
% type and obtain the transpose of it as this code crashes or gives 
% incomplete output. The following has been done below. 

X = double(Xte');
Y = double(Yte');
n_data = 500;

% Depending on the memory available in the computer, the code manages to
% run smoothly only for those many number of rows. Use the variable n_data
% to run the code for those many number of rows. i5, 2.7GHz and 8GB system
% runs upto 2000 rows but takes nearly 30 minutes. Set the value accordingly. 

for i=1:n_data
 a(i,:) = X(i,:);
 b(i,:) = Y(i,:);
 end
X = a;
Y = b;

%% Train a clustering random forest
% Parameters
ntree = 200;
mtry = -1;
extra_options.proximity = 1;
extra_options.nodesize = 1;

% classRF_train plots random forest using the parameters as mentioned.
% Empty square brackets correspond to Y which is obtained dynamically and
% stored as a single dimensional array in this case. There are many other
% parameters but the first 4 are of utmost importance to us. 

RF_model = classRF_train(X, [], ntree, mtry, extra_options);

%% Build affinity matrix
% The affinity matrix by the binary affinity model
disp('To construct affinity by ClustRF-Strct(Bi)');
A_Bi = RF_model.proximity;
figure(1);
imagesc(A_Bi);
title('ClustRF-Bi');

% The affinity matrix by the uniform ClustRF-Strct model
disp('To construct affinity by ClustRF-Strct(Unfm)');
A_Unfm = build_ClustRF_Strct_A(X, RF_model, 'Uniform');
figure(2);
imagesc(A_Unfm);
title('ClustRF-Strct (Unfm)');

% The affinity matrix by the adaptive ClustRF-Strct model
disp('To construct affinity by ClustRF-Strct(Adpt)');
A_Adpt = build_ClustRF_Strct_A(X, RF_model, 'Adaptive');
figure(3);
imagesc(A_Adpt);
title('ClustRF-Strct (Adpt)');

%% Perform spectral clustering (check SPClustering.m)
num_clst = 6;
Cl_Bi = SPClustering(A_Bi, num_clst);
Cl_Unfm = SPClustering(A_Unfm, num_clst);
Cl_Adpt = SPClustering(A_Adpt, num_clst);

%% Compare the clustering results (check adjust_rand_index.m)
ARI_Bi = adjust_rand_index(Cl_Bi, Y);
ARI_Unfm = adjust_rand_index(Cl_Unfm, Y);
ARI_Adpt = adjust_rand_index(Cl_Adpt, Y);

fprintf('ARI score comparison: \nClustRF_Bi: %f \nClustRF_Strct(Unfm): %f \nClustRF_Strct(Adpt): %f\n', ARI_Bi, ARI_Unfm, ARI_Adpt);