///////////////////////////////////////////////////////////////////////////////
//
// Function for running arbitrary code and timing it.
//
// Author: Joe Carnahan <joseph.carnahan@gmail.com>
// Last Modified: 2012-01-14
//
///////////////////////////////////////////////////////////////////////////////

#if !defined( _RUNNER_JCC_ )
#define PROJECTEULER_RUNNER_H_

#include <ctime>
#include <string>

using std::string;

namespace run {

typedef string (*toRun)(void);

void   PrintAndTime(const toRun, const string);
double TimeInSeconds(const toRun);

}

#endif
