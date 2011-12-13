///////////////////////////////////////////////////////////////////////////////
//
// Function for running arbitrary code and timing it.
//
// Author: Joe Carnahan <joseph.carnahan@gmail.com>
// Last Modified: 2011-12-12
//
///////////////////////////////////////////////////////////////////////////////

#if !defined( _RUNNER_JCC_ )
#define _RUNNER_JCC_

#include <ctime>
#include <string>

using std::string;

typedef string (*toRun)(void);

void   printAndTime(toRun, string);
time_t timeInSeconds(toRun);

#endif
